package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

public class RemoveItemUsecaseImpl implements RemoveItemUsecase {
    private final Repository repository;
    private PantryItem pantryItem;


    public RemoveItemUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    public PantryItem getPantryItem() {
        return pantryItem;
    }

    @Override
    public void init(final PantryItem pantryItem) {
        this.pantryItem = pantryItem;
    }

    @Override
    public Observable<PantryItem> execute() {
        return null;
    }
}
