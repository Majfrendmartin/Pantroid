package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItemType;

/**
 * Created by Majfrendmartin on 2016-11-18.
 */

public interface RetrievePantryItemTypeUsecase extends Usecase<PantryItemType> {
    void init(Long itemId);
}
