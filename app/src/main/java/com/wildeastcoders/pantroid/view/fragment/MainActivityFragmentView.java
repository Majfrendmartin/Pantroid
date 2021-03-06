package com.wildeastcoders.pantroid.view.fragment;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.view.View;

import java.util.List;


/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface MainActivityFragmentView extends View {
    void onPantryItemsListChanged(List<PantryItem> pantryItemList);

    void onDisplayLongClickMenu(@NonNull final PantryItem item);

    void onNavigateToEditItemActivity(@NonNull final PantryItem item);

    void onUpdateItem(@NonNull final PantryItem item);

    void onDisplayGetItemsError(Throwable throwable);

    void onDisplayRemoveItemsError(Throwable throwable);

    void onPantryItemRemoved(PantryItem item);
}
