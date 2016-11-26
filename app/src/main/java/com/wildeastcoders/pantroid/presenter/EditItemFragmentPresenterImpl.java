package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImpl extends AbstractPresenter<EditItemFragmentView>
        implements EditItemFragmentPresenter {
    private final PantryItemValidator pantryItemValidator;
    private final RetrievePantryItemUsecase retrievePantryItemUsecase;
    private final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase;

    @Nullable
    private Subscription retrievePantryItemTypesSubsctibtion;

    @Nullable
    private List<PantryItemType> itemTypesCache;

    @Nullable
    private PantryItem itemCache;

    public EditItemFragmentPresenterImpl(final PantryItemValidator pantryItemValidator,
                                         final RetrievePantryItemUsecase retrievePantryItemUsecase,
                                         final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase) {
        this.pantryItemValidator = pantryItemValidator;
        this.retrievePantryItemUsecase = retrievePantryItemUsecase;
        this.retrievePantryItemTypesUsecase = retrievePantryItemTypesUsecase;
    }

    @Override
    public void onSaveItemClicked(String name, PantryItemType type, int quantity, Date addingDate, Date bestBeforeDate) {

    }

    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);

        if (bundle.containsKey(KEY_EDIT_ITEM_ID)) {
            final int itemId = bundle.getInt(KEY_EDIT_ITEM_ID);
            retrieveItemDetails(itemId);
        } else {
            populatePantryItemTypes();
        }

    }

    private void retrieveItemDetails(final int itemId) {
        final Observable<List<PantryItemType>> retrievePantryItemTypesObservable = createRetrievePantryItemTypesObservable();
        final Observable<PantryItem> retrieveEditPantryItemObservable = createRetrieveEditPantryItemObservable(itemId);
        Observable.zip(retrievePantryItemTypesObservable,
                retrieveEditPantryItemObservable,
                (itemTypes, pantryItem) -> new RetrieveResult(itemTypes, pantryItem))
                .subscribe(this::handleDataRetrieveResult);
    }

    private void handleDataRetrieveResult(final RetrieveResult retrieveResult) {
        if (retrieveResult.itemTypes == null || retrieveResult.itemTypes.isEmpty()) {
            handleRetrievePantryItemTypesError(null);
            return;
        }

        if (retrieveResult.item == null) {
            handleRetrievePantryItemError();
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
            createRetrievePantryItemTypesObservable()
                    .onErrorReturn(throwable -> {
                        handleRetrievePantryItemTypesError(throwable);
                        return null;
                    })
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

    private Observable<PantryItem> createRetrieveEditPantryItemObservable(final int itemId) {
        retrievePantryItemUsecase.init(itemId);
        return retrievePantryItemUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void handleRetrievePantryItemTypesError(@Nullable final Throwable throwable) {
        //TODO: handle error
    }

    private void handleRetrievePantryItemError() {
        //TODO: handle error
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
}
