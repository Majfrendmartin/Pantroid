package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemUsecase;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import java.util.Date;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImpl extends AbstractPresenter<EditItemFragmentView>
        implements EditItemFragmentPresenter {
    private final PantryItemValidator pantryItemValidator;
    private final GetPantryItemUsecase getPantryItemUsecase;
    private final GetPantryItemTypesUsecase getPantryItemTypesUsecase;

    public EditItemFragmentPresenterImpl(final PantryItemValidator pantryItemValidator,
                                         final GetPantryItemUsecase getPantryItemUsecase,
                                         final GetPantryItemTypesUsecase getPantryItemTypesUsecase) {
        this.pantryItemValidator = pantryItemValidator;
        this.getPantryItemUsecase = getPantryItemUsecase;
        this.getPantryItemTypesUsecase = getPantryItemTypesUsecase;
    }

    @Override
    public void onSaveItemClicked(String name, PantryItemType type, int quantity, Date addingDate, Date bestBeforeDate) {

    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: handle fragment creation
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
