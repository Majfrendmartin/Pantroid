package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */

public interface UpdateItemQuantityUsecase extends Usecase<PantryItem> {
    enum QuantityUpdateOperation {
        INCREASE,
        DECREASE
    }

    void init(@NonNull PantryItem item, @NonNull QuantityUpdateOperation operation);
}
