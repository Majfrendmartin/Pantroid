package com.wildeastcoders.pantroid.model.database;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.List;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface Repository {
    //TODO: add sorting and filtering
    Observable<List<PantryItem>> getItems();

    Observable<List<PantryItemType>> getTypes();

    Observable<PantryItem> addItem(PantryItem item);

    Observable<PantryItemType> addType(PantryItemType item);

    Observable<PantryItem> updateItem(PantryItem item);

    Observable<PantryItemType> updateType(PantryItemType item);

    Observable<Void> removeItem(PantryItem item);

    Observable<Void> removeType(PantryItemType item);
}
