package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.Constants;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-04.
 */
public class RetrievePantryItemUsecaseImpl implements RetrievePantryItemUsecase {
    private final Repository repository;
    private Long itemId;

    public RetrievePantryItemUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void init(final Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public Observable<PantryItem> execute() {
        if (itemId == null) {
            return Observable.error(new NullPointerException(Constants.MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT));
        }
        return repository.getItemById(itemId);
    }

    public Long getItemId() {
        return itemId;
    }
}
