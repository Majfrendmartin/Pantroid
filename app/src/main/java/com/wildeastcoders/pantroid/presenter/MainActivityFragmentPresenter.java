package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface MainActivityFragmentPresenter extends Presenter<MainActivityFragmentView> {
    List<PantryItem> getPantryItemsUpdate();

    void onItemLongClicked(@NonNull final PantryItem pantryItem);

    void onIncreseItemsCountClicked(@NonNull final PantryItem pantryItem);

    void onDecreseItemsCountClicked(@NonNull final PantryItem pantryItem);

    void onRemoveItemClicked(@NonNull final PantryItem pantryItem);

    void onEditItemClicked(@NonNull final PantryItem pantryItem);
}
