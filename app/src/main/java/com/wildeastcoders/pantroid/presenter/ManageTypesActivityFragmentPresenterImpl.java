package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RemoveTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.view.fragment.ManageTypesActivityFragmentView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class ManageTypesActivityFragmentPresenterImpl extends AbstractPresenter<ManageTypesActivityFragmentView> implements ManageTypesActivityFragmentPresenter {
    private final RemoveTypeUsecase removeTypeUsecase;
    private final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase;
    private List<PantryItemType> itemTypes = new CopyOnWriteArrayList<>();
    private Subscription retrieveTypesSubscription;
    private Subscription removeTypesSubscription;

    public ManageTypesActivityFragmentPresenterImpl(final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase, final RemoveTypeUsecase removeTypeUsecase) {
        this.retrievePantryItemTypesUsecase = retrievePantryItemTypesUsecase;
        this.removeTypeUsecase = removeTypeUsecase;
    }

    @Override
    public void onItemLongClicked(final PantryItemType item) {
        if (isViewBounded()) {
            getView().displayItemOptions(item);
        }
    }

    @Override
    public void onRemoveItemClicked(@NonNull final PantryItemType item) {
        removeTypeUsecase.init(item);
        removeTypesSubscription = removeTypeUsecase.execute()
                .onErrorReturn(throwable -> null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::handleItemRemoved);
    }

    private void handleItemRemoved(final PantryItemType pantryItemType) {
        if (pantryItemType == null) {
            if (isViewBounded()) {
                getView().displayRemoveItemFailed();
            }
        } else {
            retrieveTypes();
        }
    }

    @Override
    public void onEditItemClicked(final PantryItemType item) {
        if (isViewBounded()) {
            getView().displayEditTypeDialog(item);
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);
        retrieveTypes();
    }

    private void retrieveTypes() {
         retrieveTypesSubscription = getRetrieveObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handlePantryItemTypesRetrieved);
    }

    @NonNull
    private Observable<List<PantryItemType>> getRetrieveObservable() {
        return retrievePantryItemTypesUsecase.execute()
                .onErrorReturn(throwable -> null);
    }

    private void handlePantryItemTypesRetrieved(final List<PantryItemType> itemTypes) {
        final boolean viewBounded = isViewBounded();
        final ManageTypesActivityFragmentView view = getView();
        if (itemTypes != null) {
            this.itemTypes.clear();
            this.itemTypes.addAll(itemTypes);

            if (viewBounded) {
                view.onItemsListChanged(this.itemTypes);
            }

        } else if (viewBounded) {
            view.displayRetrieveTypesError();
        }
    }

    @Override
    public void onDestroy() {
        if (retrieveTypesSubscription != null && retrieveTypesSubscription.isUnsubscribed()) {
            retrieveTypesSubscription.unsubscribe();
        }

        if (removeTypesSubscription != null && removeTypesSubscription.isUnsubscribed()) {
            removeTypesSubscription.unsubscribe();
        }
    }
}
