package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItemType;

/**
 * Created by Majfrendmartin on 11.11.2016.
 */

public interface RemoveTypeUsecase extends Usecase<PantryItemType> {
    void init(PantryItemType pantryItemType);

    PantryItemType getPantryItemType();
}
