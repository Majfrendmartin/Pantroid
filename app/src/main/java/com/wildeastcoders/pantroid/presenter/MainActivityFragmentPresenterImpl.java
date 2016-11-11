package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.DECREASE;
import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.INCREASE;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class MainActivityFragmentPresenterImpl extends AbstractPresenter<MainActivityFragmentView>
        implements MainActivityFragmentPresenter {

    private final GetPantryItemsUsecase getPantryItemsUsecase;
    private final UpdateItemQuantityUsecase updateItemQuantityUsecase;

    private Subscription getPantryItemsSubscribtion;
    private Subscription updateItemQuantitySubscribtion;

    public MainActivityFragmentPresenterImpl(final GetPantryItemsUsecase getPantryItemsUsecase,
                                             final UpdateItemQuantityUsecase updateItemQuantityUsecase) {
        this.getPantryItemsUsecase = getPantryItemsUsecase;
        this.updateItemQuantityUsecase = updateItemQuantityUsecase;
    }

    @Override
    public void requestPantryItemsUpdate() {
        getPantryItemsSubscribtion = getPantryItemsUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleGetDataError(throwable);
                    return null;
                })
                .subscribe(pantryItemList -> {
                    if (isViewBound()) {
                        getView().onPantryItemsListChanged(pantryItemList);
                    }
                });
    }

    private void handleGetDataError(final Throwable throwable) {
        if (isViewBound()) {
            getView().onDisplayGetItemsError(throwable);
        }
    }

    @Override
    public void onItemLongClicked(@NonNull final PantryItem pantryItem) {
        if (isViewBound()) {
            getView().onDisplayLongClickMenu(pantryItem);
        }
    }

    @Override
    public void onIncreaseItemsCountClicked(@NonNull final PantryItem pantryItem) {
        performUpdateItemsQuantity(INCREASE);
    }

    @Override
    public void onDecreaseItemsCountClicked(@NonNull final PantryItem pantryItem) {
        performUpdateItemsQuantity(DECREASE);
    }

    private void performUpdateItemsQuantity(final QuantityUpdateOperation operation) {
        updateItemQuantityUsecase.init(operation);
        updateItemQuantitySubscribtion = updateItemQuantityUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleGetDataError(throwable);
                    return null;
                })
                .subscribe(resultPantryItem -> {
                    if (isViewBound()) {
                        getView().onUpdateItem(resultPantryItem);
                    }
                });
    }

    @Override
    public void onRemoveItemClicked(@NonNull final PantryItem pantryItem) {

    }

    @Override
    public void onEditItemClicked(@NonNull final PantryItem pantryItem) {

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
    public void onSaveInstanceState(final Bundle bundle) {

    }
}
