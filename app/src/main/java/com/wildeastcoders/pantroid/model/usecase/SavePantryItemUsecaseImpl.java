package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.Date;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-08.
 */
public class SavePantryItemUsecaseImpl implements SavePantryItemUsecase {
    private PantryItem item;

    @Override
    public void init(final PantryItem pantryItem) {
        item = pantryItem;
    }

    @Override
    public void init(@NonNull final String itemName, @NonNull final PantryItemType pantryItemType,
                     final int quantity, @NonNull final Date addingDate,
                     @NonNull final Date bestBeforeDate) {
        item = new PantryItem(null, itemName, pantryItemType.getId(), addingDate, bestBeforeDate, quantity);
    }

    @Override
    public Observable<PantryItem> execute() {
        return null;
    }

    /**
     * Package visible method for tests use only.
     * @return
     */
    PantryItem getItem() {
        return item;
    }
}
