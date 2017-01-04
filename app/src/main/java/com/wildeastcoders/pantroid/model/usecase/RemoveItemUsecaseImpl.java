package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT;

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
        if (pantryItem == null) {
            return Observable.error(new NullPointerException(MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT));
        }

        return repository.removeItem(pantryItem).map(aVoid -> pantryItem);
    }
}
