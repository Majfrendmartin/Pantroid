package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditItemFragmentPresenter extends Presenter<EditItemFragmentView> {
    void onSaveItemClicked(String name, PantryItemType type, int quantity, Date addingDate,
                           Date bestBeforeDate);

    void validateName(String name);

    void validateType(PantryItemType type);

    void validateQuantity(int quantity);

    void validateAddingDate(Date addingDate);

    void validateBestBeforeDate(Date addingDate);
}
