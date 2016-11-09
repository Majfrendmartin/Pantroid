package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.ManageTypesFragmentView;

import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface ManageTypesFragmentPresenter extends Presenter<ManageTypesFragmentView> {
    void onItemLongClicked(PantryItemType item);
    void onRemoveItemClicked(PantryItemType item);
    void onEditItemClicked(PantryItemType item);
}
