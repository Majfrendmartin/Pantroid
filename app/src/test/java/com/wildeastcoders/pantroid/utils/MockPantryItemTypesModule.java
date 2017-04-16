package com.wildeastcoders.pantroid.utils;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 2017-03-10.
 */

public class MockPantryItemTypesModule extends PantryItemTypesModule {

    private final Presenter presenter;

    public MockPantryItemTypesModule(@NonNull ManageTypesActivityPresenter manageTypesActivityPresenter) {
        this.presenter = manageTypesActivityPresenter;
    }

    public MockPantryItemTypesModule(@NonNull EditTypeFragmentPresenter editTypeFragmentPresenter) {
        this.presenter = editTypeFragmentPresenter;
    }

    public MockPantryItemTypesModule(@NonNull EditItemFragmentPresenter EditItemFragmentPresenter) {
        this.presenter = EditItemFragmentPresenter;
    }

    @Override
    public EditTypeFragmentPresenter provideEditTypeFragmentPresenter(SavePantryItemTypeUsecase savePantryItemTypeUsecase, RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase, FieldsValidator fieldsValidator, EventBus eventBus) {
        return (EditTypeFragmentPresenter) presenter;
    }

    @Override
    public ManageTypesActivityPresenter provideManageTypesActivityPresenter() {
        return (ManageTypesActivityPresenter) presenter;
    }
}
