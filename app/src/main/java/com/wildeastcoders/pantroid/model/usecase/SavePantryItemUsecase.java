package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.Date;

/**
 * Created by Majfrendmartin on 06.12.2016.
 */

public interface SavePantryItemUsecase extends Usecase<PantryItem> {
    void init(PantryItem pantryItem);

    void init(String itemName, PantryItemType pantryItemType, int quantity, Date addingDate, Date bestBeforeDate);
}
