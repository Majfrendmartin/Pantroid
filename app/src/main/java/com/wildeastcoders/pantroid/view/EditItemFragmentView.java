package com.wildeastcoders.pantroid.view;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditItemFragmentView extends View {
    void populateTypesSpinner(List<PantryItemType> types);

    void displayItemDetails(PantryItem item);
}
