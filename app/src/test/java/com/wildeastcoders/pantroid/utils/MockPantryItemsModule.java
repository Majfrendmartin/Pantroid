package com.wildeastcoders.pantroid.utils;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenterImpl;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;

import org.greenrobot.eventbus.EventBus;

import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-19.
 */

public class MockPantryItemsModule extends PantryItemsModule {

    @NonNull
    private final Presenter presenter;

    public MockPantryItemsModule(@NonNull EditItemActivityPresenter editItemActivityPresenter) {
        presenter = editItemActivityPresenter;
    }

    public MockPantryItemsModule(@NonNull MainActivityPresenter mainActivityPresenter) {
        presenter = mainActivityPresenter;
    }

    public MockPantryItemsModule(@NonNull EditItemFragmentPresenter editItemFragmentPresenter) {
        presenter = editItemFragmentPresenter;
    }

    @Override
    public EditItemActivityPresenter provideEditItemActivityPresenter(@NonNull EventBus eventBus) {
        return (EditItemActivityPresenter) presenter;
    }

    @Override
    public MainActivityPresenter provideMainActivityPresenter() {
        return (MainActivityPresenter) presenter;
    }

    @Override
    public EditItemFragmentPresenter provideEditItemFragmentPresenter(
            FieldsValidator fieldsValidator,
            RetrievePantryItemUsecase retrievePantryItemUsecase,
            RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase,
            SavePantryItemUsecase savePantryItemUsecase,
            EventBus eventBus) {
        return (EditItemFragmentPresenter) presenter;
    }
}
