package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-12.
 */

public class UpdateItemQuantityUsecaseImpl implements UpdateItemQuantityUsecase {
    public static final String USECASE_NOT_INITIALIZED = "Usecase not initialized";
    private final Repository repository;
    private PantryItem pantryItem;
    private QuantityUpdateOperation quantityUpdateOperation;

    public UpdateItemQuantityUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void init(@NonNull final PantryItem pantryItem, @NonNull final QuantityUpdateOperation operation) {
        this.pantryItem = pantryItem;
        this.quantityUpdateOperation = operation;
    }

    @NonNull
    @Override
    public Observable<PantryItem> execute() {
        if (pantryItem == null || quantityUpdateOperation == null) {
            return Observable.error(new NullPointerException(USECASE_NOT_INITIALIZED));
        }
        final int pantryItemQuantity = pantryItem.getQuantity();

        if (pantryItemQuantity == Integer.MAX_VALUE || pantryItemQuantity == 0) {
            //Max/min value reached. Nothing to do here. Return observable with same object.
            return Observable.just(pantryItem);
        }
        pantryItem.setQuantity(pantryItemQuantity + (quantityUpdateOperation == QuantityUpdateOperation.INCREASE ? 1 : -1));
        return repository.updateItem(pantryItem);
    }

    /**
     * Only for unit tests.
     * @return
     */
    PantryItem getPantryItem() {
        return pantryItem;
    }

    /**
     * Only for unit tests.
     * @return
     */
    QuantityUpdateOperation getQuantityUpdateOperation() {
        return quantityUpdateOperation;
    }
}
