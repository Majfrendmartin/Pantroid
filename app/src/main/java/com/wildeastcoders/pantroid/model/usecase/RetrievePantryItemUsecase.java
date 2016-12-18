package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;

/**
 * Created by Majfrendmartin on 2016-11-18.
 */

public interface RetrievePantryItemUsecase extends Usecase<PantryItem> {
    void init(Long itemId);
}
