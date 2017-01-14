package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import java.util.ArrayList;
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

    private final RetrievePantryItemsUsecase retrievePantryItemsUsecase;
    private final UpdateItemQuantityUsecase updateItemQuantityUsecase;
    private final RemoveItemUsecase removeItemUsecase;

    //TODO: handle subscribtions during lifecycle.
    private Subscription getPantryItemsSubscribtion;
    private Subscription removeItemSubscribtion;
    private Subscription updateItemQuantitySubscribtion;

    private List<PantryItem> cachedItemsList;

    public MainActivityFragmentPresenterImpl(final RetrievePantryItemsUsecase retrievePantryItemsUsecase,
                                             final UpdateItemQuantityUsecase updateItemQuantityUsecase,
                                             final RemoveItemUsecase removeItemUsecase) {
        this.retrievePantryItemsUsecase = retrievePantryItemsUsecase;
        this.updateItemQuantityUsecase = updateItemQuantityUsecase;
        this.removeItemUsecase = removeItemUsecase;
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
        if (getPantryItemsSubscribtion != null && !getPantryItemsSubscribtion.isUnsubscribed()) {
            getPantryItemsSubscribtion.unsubscribe();
        }

        getPantryItemsSubscribtion = retrievePantryItemsUsecase.execute()
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
                    if (isViewBounded()) {
                        getView().onPantryItemsListChanged(cachedItemsList);
                    }
                });
    }

    private void handleGetDataError(final Throwable throwable) {
        if (isViewBounded()) {
            getView().onDisplayGetItemsError(throwable);
        }
    }

    private void handleRemoveItemError(final Throwable throwable) {
        if (isViewBounded()) {
            getView().onDisplayRemoveItemsError(throwable);
        }
    }

    @Override
    public void onItemLongClicked(@NonNull final PantryItem pantryItem) {
        if (isViewBounded()) {
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
        updateItemQuantityUsecase.init(pantryItem, operation);
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
                        if (isViewBounded()) {
                            getView().onUpdateItem(pantryItem);
                        }
                    }
                });
    }

    @Override
    public void onRemoveItemClicked(@NonNull final PantryItem pantryItem) {
        removeItemUsecase.init(pantryItem);
        removeItemSubscribtion = removeItemUsecase.execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    handleRemoveItemError(throwable);
                    return null;
                })
                .subscribe(removedItem -> {
                    if (removedItem != null) {
                        if (cachedItemsList != null && cachedItemsList.contains(removedItem)) {
                            cachedItemsList.remove(removedItem);
                        }
                        if (isViewBounded()) {
                            getView().onPantryItemRemoved(pantryItem);
                        }
                    }
                });
    }

    @Override
    public void onEditItemClicked(@NonNull final PantryItem pantryItem) {
        if (isViewBounded()) {
            getView().onNavigateToEditItemActivity(pantryItem);
        }
    }


}
