package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT;

/**
 * Created by Majfrendmartin on 2017-01-23.
 */
public class RetrievePantryItemTypeUsecaseImpl implements RetrievePantryItemTypeUsecase {

    private final Repository repository;
    private Long itemId;

    public RetrievePantryItemTypeUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void init(final Long itemId) {
        this.itemId = itemId;
    }

    @NonNull
    @Override
    public Observable<PantryItemType> execute() {
        if (itemId == null) {
            return Observable.error(new NullPointerException(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT));
        }
        return repository.getTypeById(itemId);
    }

    Long getItemId() {
        return itemId;
    }
}
