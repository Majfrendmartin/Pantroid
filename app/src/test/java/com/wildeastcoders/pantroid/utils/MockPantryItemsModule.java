package com.wildeastcoders.pantroid.utils;

import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 2017-02-19.
 */

public class MockPantryItemsModule extends PantryItemsModule {

    private final Presenter presenter;

    public MockPantryItemsModule(EditItemActivityPresenter editItemActivityPresenter) {
        presenter = editItemActivityPresenter;
    }

    public MockPantryItemsModule(MainActivityPresenter mainActivityPresenter) {
        presenter = mainActivityPresenter;
    }

    @Override
    public EditItemActivityPresenter provideEditItemActivityPresenter(EventBus eventBus) {
        return (EditItemActivityPresenter) presenter;
    }

    @Override
    public MainActivityPresenter provideMainActivityPresenter() {
        return (MainActivityPresenter) presenter;
    }
}
