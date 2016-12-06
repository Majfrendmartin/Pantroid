package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;

/**
 * Created by Majfrendmartin on 06.12.2016.
 */

public interface SavePantryItemUsecase extends Usecase<PantryItem> {
    void init(PantryItem pantryItem);
}
