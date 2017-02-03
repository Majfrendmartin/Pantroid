package com.wildeastcoders.pantroid.view;

import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface ManageTypesFragmentView extends View {
    void onItemsListChanged(List<PantryItemType> itemTypes);

    void displayEditTypeDialog(PantryItemType item);

    void displayCreateTypeDialog();

    void onUpdateItem(int index, PantryItemType item);

    void displayRetrieveTypesError();

    void displayItemOptions(PantryItemType pantryItemType);

    void displayRemoveItemFailed();
}
