package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;
import java.util.List;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditItemFragmentPresenter extends Presenter<EditItemFragmentView> {
    void onSaveItemClicked(String name, PantryItemType type, int quantity, Date addingDate,
                           Date bestBeforeDate);

    @Nullable
    List<PantryItemType> getItemTypesCache();

    @Nullable
    PantryItem getPantryItem();
}