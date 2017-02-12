package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.model.event.SaveItemClickedEvent;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.view.EditItemActivityFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.model.PantryItemFieldType.ADDING_DATE;
import static com.wildeastcoders.pantroid.model.PantryItemFieldType.BEST_BEFORE_DATE;
import static com.wildeastcoders.pantroid.model.PantryItemFieldType.NAME;
import static com.wildeastcoders.pantroid.model.PantryItemFieldType.QUANTITY;
import static com.wildeastcoders.pantroid.model.PantryItemFieldType.TYPE;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;
import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_ITEM_ID;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImpl extends AbstractPresenter<EditItemActivityFragmentView>
        implements EditItemFragmentPresenter {

    public static final int VALIDATION_RESULTS_CAPACITY = 5;

    private final FieldsValidator fieldsValidator;
    private final RetrievePantryItemUsecase retrievePantryItemUsecase;
    private final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase;
    private final SavePantryItemUsecase savePantryItemUsecase;


    @Nullable
    private List<PantryItemType> itemTypesCache;

    @Nullable
    private PantryItem itemCache;

    @Nullable
    private Subscription retrieveItemDetailsSubscription;

    @Nullable
    private Subscription retrievePantryItemTypesSubscription;

    @Nullable
    private Subscription savePantryItemSubscription;

    public EditItemFragmentPresenterImpl(final FieldsValidator fieldsValidator,
                                         final RetrievePantryItemUsecase retrievePantryItemUsecase,
                                         final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase,
                                         final SavePantryItemUsecase savePantryItemUsecase) {
        this.fieldsValidator = fieldsValidator;
        this.retrievePantryItemUsecase = retrievePantryItemUsecase;
        this.retrievePantryItemTypesUsecase = retrievePantryItemTypesUsecase;
        this.savePantryItemUsecase = savePantryItemUsecase;
    }


    @Override
    public void onSaveItemClicked(@NonNull String name, @NonNull PantryItemType type, int quantity,
                                  @NonNull Date addingDate, @NonNull Date bestBeforeDate) {

        if (!verifyItemHasChanged(name, type, quantity, addingDate, bestBeforeDate)) {
            if (isViewBounded()) {
                getView().displayNothingChangedDialog();
            }
            return;
        }

        final Map<PantryItemFieldType,ValidationResult> validationResults = new HashMap<>(VALIDATION_RESULTS_CAPACITY);

        handleValidationResult(NAME, fieldsValidator.validateName(name), validationResults);
        handleValidationResult(TYPE, fieldsValidator.validateType(type), validationResults);
        handleValidationResult(QUANTITY, fieldsValidator.validateQuantity(quantity), validationResults);
        handleValidationResult(ADDING_DATE, fieldsValidator.validateAddingDate(addingDate), validationResults);
        handleValidationResult(BEST_BEFORE_DATE, fieldsValidator.validateBestBeforeDate(addingDate, bestBeforeDate), validationResults);

        if (!validationResults.isEmpty()) {
            if (isViewBounded()) {
                getView().displayValidationResults(validationResults);
            }
            return;
        }

        savePantryItemUsecase.init(name, type, quantity, addingDate, bestBeforeDate);
        savePantryItemSubscription = savePantryItemUsecase.execute()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    if (isViewBounded()) {
                        getView().displayDataErrorDialog();
                    }
                    return null;
                })
                .subscribe(pantryItem -> {
                    if (pantryItem != null) {
                        itemCache = pantryItem;
                        if (isViewBounded()) {
                            getView().displayPantryItemSavedDialog(itemCache);
                        }
                    }
                });
    }

    private boolean verifyItemHasChanged(final String name, final PantryItemType type, final int quantity, final Date addingDate, final Date bestBeforeDate) {
        if (itemCache == null) {
            return true;
        }

        return !itemCache.getName().equals(name) ||
                !itemCache.getType().equals(type) ||
                (itemCache.getQuantity() != quantity) ||
                !itemCache.getAddingDate().equals(addingDate) ||
                !itemCache.getBestBeforeDate().equals(bestBeforeDate);
    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);

        EventBus.getDefault().register(this);

        if (bundle != null && bundle.containsKey(KEY_EDIT_ITEM_ID)) {
            final Long itemId = bundle.getLong(KEY_EDIT_ITEM_ID);
            retrieveItemDetails(itemId);
        } else {
            populatePantryItemTypes();
        }
    }

    private void retrieveItemDetails(final Long itemId) {
        final Observable<List<PantryItemType>> retrievePantryItemTypesObservable = createRetrievePantryItemTypesObservable();
        final Observable<PantryItem> retrievePantryItemObservable = createRetrievePantryItemObservable(itemId);
        retrieveItemDetailsSubscription = Observable.zip(retrievePantryItemTypesObservable,
                retrievePantryItemObservable,
                (itemTypes, pantryItem) -> new RetrieveResult(itemTypes, pantryItem))
                .subscribe(this::handleDataRetrieveResult);
    }

    private void handleDataRetrieveResult(final RetrieveResult retrieveResult) {
        if (retrieveResult.itemTypes == null) {
            return;
        }

        if (retrieveResult.itemTypes.isEmpty()) {
            handleRetrievePantryItemTypesError();
            return;
        }

        if (retrieveResult.item == null) {
            return;
        }

        handlePantryItemTypesRetrieved(retrieveResult.itemTypes);
        handlePantryItemRetrieved(retrieveResult.item);

    }

    private void handlePantryItemRetrieved(final PantryItem item) {
        itemCache = item;
        if (isViewBounded()) {
            final EditItemActivityFragmentView view = getView();
            view.setupNameField(item.getName());
            view.setupTypeField(item.getType());
            view.setupQuantityField(item.getQuantity());
            view.setupAddingDateField(item.getAddingDate());
            view.setupBestBeforeField(item.getBestBeforeDate());
        }
    }

    private void populatePantryItemTypes() {
        if (itemTypesCache == null || itemTypesCache.isEmpty()) {
            retrievePantryItemTypesSubscription = createRetrievePantryItemTypesObservable()
                    .subscribe(itemTypes -> {
                        handlePantryItemTypesRetrieved(itemTypes);
                    });
        } else {
            performPopulateTypeSpinner(itemTypesCache);
        }
    }

    private void handlePantryItemTypesRetrieved(final List<PantryItemType> itemTypes) {
        itemTypesCache = itemTypes;
        performPopulateTypeSpinner(itemTypes);
    }

    private void performPopulateTypeSpinner(final List<PantryItemType> itemTypes) {
        if (isViewBounded()) {
            getView().populateTypesSpinner(itemTypes);
        }
    }

    private Observable<PantryItem> createRetrievePantryItemObservable(final Long itemId) {
        retrievePantryItemUsecase.init(itemId);
        return retrievePantryItemUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleRetrievePantryItemError();
                    return null;
                });
    }

    /**
     * Getter for test purpose only.
     *
     * @return Cached items types.
     */

    @Nullable
    @Override
    public List<PantryItemType> getItemTypesCache() {
        return itemTypesCache;
    }

    @Nullable
    @Override
    public PantryItem getPantryItem() {
        return itemCache;
    }

    @Override
    public void setPantryItem(final PantryItem pantryItem) {
        itemCache = pantryItem;
    }

    private Observable<List<PantryItemType>> createRetrievePantryItemTypesObservable() {
        return retrievePantryItemTypesUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleRetrievePantryItemTypesError();
                    return null;
                });
    }

    private void handleRetrievePantryItemTypesError() {
        if (isViewBounded()) {
            getView().displayTypeErrorDialog();
        }
    }

    private void handleRetrievePantryItemError() {
        if (isViewBounded()) {
            getView().displayDataErrorDialog();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }

    @Override
    public void handleFinishDialogConfirmButtonClicked() {
        if (isViewBounded()) {
            getView().finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (isViewBounded()) {
            getView().displayDiscardChangesDialog();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);

        if (retrievePantryItemTypesSubscription != null && retrievePantryItemTypesSubscription.isUnsubscribed()){
            retrievePantryItemTypesSubscription.unsubscribe();
        }

        if (retrieveItemDetailsSubscription != null && retrieveItemDetailsSubscription.isUnsubscribed()){
            retrieveItemDetailsSubscription.unsubscribe();
        }

        if (savePantryItemSubscription != null && savePantryItemSubscription.isUnsubscribed()){
            savePantryItemSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    @Subscribe
    public void onSaveItemClickedEvent(@NonNull final SaveItemClickedEvent saveItemClickedEvent) {
        if (isViewBounded()) {
            getView().requestInputDataOnSaveItemClicked();
        }
    }

    private void handleValidationResult(PantryItemFieldType type, final ValidationResult validationResult, Map<PantryItemFieldType, ValidationResult> map) {
        if (validationResult != VALID) {
            map.put(type, validationResult);
        }
    }

    private class RetrieveResult {
        private List<PantryItemType> itemTypes;
        private PantryItem item;

        private RetrieveResult(final List<PantryItemType> itemTypes, final PantryItem item) {
            this.itemTypes = itemTypes;
            this.item = item;
        }
    }
}
