package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT;

/**
 * Created by Majfrendmartin on 2017-02-03.
 */
public class RemoveTypeUsecaseImpl implements RemoveTypeUsecase {
    private final Repository repository;
    private PantryItemType pantryItemType;

    public RemoveTypeUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void init(final PantryItemType pantryItemType) {
        this.pantryItemType = pantryItemType;
    }

    @Override
    public PantryItemType getPantryItemType() {
        return pantryItemType;
    }

    @NonNull
    @Override
    public Observable<PantryItemType> execute() {
        if (pantryItemType == null) {
            return Observable.error(new NullPointerException(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT));
        }
        return repository
                .removeType(pantryItemType)
                .map(aVoid -> pantryItemType);
    }
}
