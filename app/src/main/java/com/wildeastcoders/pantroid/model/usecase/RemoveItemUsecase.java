package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;

/**
 * Created by Majfrendmartin on 11.11.2016.
 */

public interface RemoveItemUsecase extends Usecase<PantryItem> {
    void init(PantryItem pantryItem);

    PantryItem getPantryItem();
}
