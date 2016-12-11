package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.model.database.ValidationResult;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType.ADDING_DATE;
import static com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType.BEST_BEFORE_DATE;
import static com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType.NAME;
import static com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType.QUANTITY;
import static com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType.TYPE;
import static com.wildeastcoders.pantroid.model.database.ValidationResult.VALID;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImpl extends AbstractPresenter<EditItemFragmentView>
        implements EditItemFragmentPresenter {

    public static final int VALIDATION_RESULTS_CAPACITY = 5;

    private final PantryItemValidator pantryItemValidator;
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

    public EditItemFragmentPresenterImpl(final PantryItemValidator pantryItemValidator,
                                         final RetrievePantryItemUsecase retrievePantryItemUsecase,
                                         final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase,
    final SavePantryItemUsecase savePantryItemUsecase) {
        this.pantryItemValidator = pantryItemValidator;
        this.retrievePantryItemUsecase = retrievePantryItemUsecase;
        this.retrievePantryItemTypesUsecase = retrievePantryItemTypesUsecase;
        this.savePantryItemUsecase = savePantryItemUsecase;
    }



    @Override
    public void onSaveItemClicked(@NonNull String name, @NonNull PantryItemType type, int quantity,
                                  @NonNull Date addingDate, @NonNull Date bestBeforeDate) {

        final Map<PantryItemFieldType,ValidationResult> validationResults = new HashMap<>(VALIDATION_RESULTS_CAPACITY);

        handleValidationResult(NAME, pantryItemValidator.validateName(name), validationResults);
        handleValidationResult(TYPE, pantryItemValidator.validateType(type), validationResults);
        handleValidationResult(QUANTITY, pantryItemValidator.validateQuantity(quantity), validationResults);
        handleValidationResult(ADDING_DATE, pantryItemValidator.validateAddingDate(addingDate), validationResults);
        handleValidationResult(BEST_BEFORE_DATE, pantryItemValidator.validateBestBeforeDate(bestBeforeDate), validationResults);

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
                    //TODO: handle save/update error
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

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null && bundle.containsKey(KEY_EDIT_ITEM_ID)) {
            final int itemId = bundle.getInt(KEY_EDIT_ITEM_ID);
            retrieveItemDetails(itemId);
        } else {
            populatePantryItemTypes();
        }

    }

    private void retrieveItemDetails(final int itemId) {
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
            final EditItemFragmentView view = getView();
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

    private Observable<PantryItem> createRetrievePantryItemObservable(final int itemId) {
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

    private class RetrieveResult {
        private List<PantryItemType> itemTypes;
        private PantryItem item;

        private RetrieveResult(final List<PantryItemType> itemTypes, final PantryItem item) {
            this.itemTypes = itemTypes;
            this.item = item;
        }
    }

    @Override
    public void onDestroy() {
        if (retrievePantryItemTypesSubscription != null && retrievePantryItemTypesSubscription.isUnsubscribed()){
            retrievePantryItemTypesSubscription.unsubscribe();
        }

        if (retrieveItemDetailsSubscription != null && retrieveItemDetailsSubscription.isUnsubscribed()){
            retrieveItemDetailsSubscription.unsubscribe();
        }

        super.onDestroy();
    }

    private void handleValidationResult(PantryItemFieldType type, final ValidationResult validationResult, Map<PantryItemFieldType, ValidationResult> map) {
        if (validationResult != VALID) {
            map.put(type, validationResult);
        }
    }
}
