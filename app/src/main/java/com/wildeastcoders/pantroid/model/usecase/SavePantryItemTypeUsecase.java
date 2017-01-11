package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItemType;

/**
 * Created by Majfrendmartin on 06.12.2016.
 */

public interface SavePantryItemTypeUsecase extends Usecase<PantryItemType> {
    void init(PantryItemType pantryItemType);

    void init(String typeName);
}
