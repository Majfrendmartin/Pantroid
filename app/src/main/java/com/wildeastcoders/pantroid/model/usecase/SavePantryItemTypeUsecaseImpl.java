package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT;

/**
 * Created by Majfrendmartin on 2017-01-11.
 */
public class SavePantryItemTypeUsecaseImpl implements SavePantryItemTypeUsecase {
    private final Repository repository;
    private PantryItemType pantryItemType;

    public SavePantryItemTypeUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void init(final PantryItemType pantryItemType) {
        this.pantryItemType = pantryItemType;
    }

    @Override
    public void init(final String typeName) {
        pantryItemType = new PantryItemType(null, typeName);
    }

    @NonNull
    @Override
    public Observable<PantryItemType> execute() {
        if (pantryItemType == null) {
            return Observable.error(new NullPointerException(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT));
        }

        return pantryItemType.getId() != null ?
                repository.updateType(pantryItemType) :
                repository.addType(pantryItemType);
    }


    public PantryItemType getPantryItemType() {
        return pantryItemType;
    }
}
