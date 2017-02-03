package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.ManageTypesFragmentView;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface ManageTypesFragmentPresenter extends Presenter<ManageTypesFragmentView> {
    void onItemLongClicked(@NonNull PantryItemType item);

    void onRemoveItemClicked(@NonNull PantryItemType item);

    void onEditItemClicked(@NonNull PantryItemType item);
}
