package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.ManageTypesActivityFragmentView;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface ManageTypesActivityFragmentPresenter extends Presenter<ManageTypesActivityFragmentView> {
    void onItemLongClicked(@NonNull PantryItemType item);

    void onRemoveItemClicked(@NonNull PantryItemType item);

    void onEditItemClicked(@NonNull PantryItemType item);
}
