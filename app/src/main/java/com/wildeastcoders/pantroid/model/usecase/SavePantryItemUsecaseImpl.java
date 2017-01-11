package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.Constants;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;

import java.util.Date;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT;

/**
 * Created by Majfrendmartin on 2017-01-08.
 */
public class SavePantryItemUsecaseImpl implements SavePantryItemUsecase {
    private final Repository repository;
    private PantryItem item;

    public SavePantryItemUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

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
        if (item == null) {
            return Observable.error(new NullPointerException(
                    MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT));
        }

        return item.getId() != null ?
                repository.updateItem(item) :
                repository.addItem(item);
    }

    /**
     * Package visible method for tests use only.
     * @return
     */
    PantryItem getItem() {
        return item;
    }
}
