package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private List<PantryItem> cachedItemsList;

    public MainActivityFragmentPresenterImpl(final GetPantryItemsUsecase getPantryItemsUsecase,
                                             final UpdateItemQuantityUsecase updateItemQuantityUsecase, RemoveItemUsecase removeItemUsecase) {
        this.getPantryItemsUsecase = getPantryItemsUsecase;
        this.updateItemQuantityUsecase = updateItemQuantityUsecase;
    }

    @Override
    public List<PantryItem> getPantryItems() {
        if (cachedItemsList == null) {
            //TODO: replace with CopyOnWriteArrayList?
            cachedItemsList = new ArrayList<>();
            requestPantryItemsUpdate();
        }
        return cachedItemsList;
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
                    if (cachedItemsList == null) {
                        cachedItemsList = pantryItemList;
                    } else {
                        cachedItemsList.clear();
                        cachedItemsList.addAll(pantryItemList);
                    }
                    if (isViewBound()) {
                        getView().onPantryItemsListChanged(cachedItemsList);
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
        performUpdateItemsQuantity(pantryItem, INCREASE);
    }

    @Override
    public void onDecreaseItemsCountClicked(@NonNull final PantryItem pantryItem) {
        performUpdateItemsQuantity(pantryItem, DECREASE);
    }

    private void performUpdateItemsQuantity(@NonNull final PantryItem pantryItem, final QuantityUpdateOperation operation) {
        updateItemQuantityUsecase.init(operation);
        updateItemQuantitySubscribtion = updateItemQuantityUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleGetDataError(throwable);
                    return null;
                })
                .subscribe(resultPantryItem -> {
                    if (resultPantryItem != null) {
                        pantryItem.update(resultPantryItem);
                        if (isViewBound()) {
                            getView().onUpdateItem(pantryItem);
                        }
                    }
                });
    }

    @Override
    public void onRemoveItemClicked(@NonNull final PantryItem pantryItem) {

    }

    @Override
    public void onEditItemClicked(@NonNull final PantryItem pantryItem) {
        if (isViewBound()) {
            getView().onNavigateToEditItemActivity(pantryItem);
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
    public void onSaveInstanceState(final Bundle bundle) {

    }
}
