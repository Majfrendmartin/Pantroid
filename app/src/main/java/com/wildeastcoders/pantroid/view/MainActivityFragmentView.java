package com.wildeastcoders.pantroid.view;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;


/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface MainActivityFragmentView extends View {
    void onPantryItemsListChanged();

    void onDisplayLongClickMenu(@NonNull final PantryItem item);

    void onNavigateToEditItemActivity(@NonNull final PantryItem item);

    void onUpdateItem(final int index);
}
