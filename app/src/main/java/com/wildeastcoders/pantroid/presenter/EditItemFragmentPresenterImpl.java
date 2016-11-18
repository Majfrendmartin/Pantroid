package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImpl extends AbstractPresenter<EditItemFragmentView>
        implements EditItemFragmentPresenter {
    private final PantryItemValidator pantryItemValidator;

    public EditItemFragmentPresenterImpl(PantryItemValidator pantryItemValidator) {
        this.pantryItemValidator = pantryItemValidator;
    }

    @Override
    public void onSaveItemClicked(String name, PantryItemType type, int quantity, Date addingDate, Date bestBeforeDate) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {

    }
}
