package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-11.
 */
public class SavePantryItemTypeUsecaseImpl implements SavePantryItemTypeUsecase {
    @Override
    public void init(final PantryItemType pantryItemType) {

    }

    @Override
    public void init(final String typeName) {

    }

    @NonNull
    @Override
    public Observable<PantryItemType> execute() {
        return null;
    }
}
