package com.wildeastcoders.pantroid.utils;

import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 2017-02-19.
 */

public class MockPantryItemsModule extends PantryItemsModule {

    private final EditItemActivityPresenter editItemActivityPresenter;

    public MockPantryItemsModule(EditItemActivityPresenter editItemActivityPresenter) {
        this.editItemActivityPresenter = editItemActivityPresenter;
    }

    @Override
    public EditItemActivityPresenter provideEditItemActivityPresenter(EventBus eventBus) {
        return editItemActivityPresenter;
    }
}
