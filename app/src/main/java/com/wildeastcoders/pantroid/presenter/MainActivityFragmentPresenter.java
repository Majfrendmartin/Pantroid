package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface MainActivityFragmentPresenter extends Presenter<MainActivityFragmentView> {

    List<PantryItem> getPantryItems();

    void requestPantryItemsUpdate();

    void onItemLongClicked(@NonNull final PantryItem pantryItem);

    void onIncreaseItemsCountClicked(@NonNull final PantryItem pantryItem);

    void onDecreaseItemsCountClicked(@NonNull final PantryItem pantryItem);

    void onRemoveItemClicked(@NonNull final PantryItem pantryItem);

    void onEditItemClicked(@NonNull final PantryItem pantryItem);
}
