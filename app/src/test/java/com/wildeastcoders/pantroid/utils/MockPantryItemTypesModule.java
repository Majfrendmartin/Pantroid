package com.wildeastcoders.pantroid.utils;

import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;

/**
 * Created by Majfrendmartin on 2017-03-10.
 */

public class MockPantryItemTypesModule extends PantryItemTypesModule {

    private final ManageTypesActivityPresenter manageTypesActivityPresenter;

    public MockPantryItemTypesModule(ManageTypesActivityPresenter manageTypesActivityPresenter) {
        this.manageTypesActivityPresenter = manageTypesActivityPresenter;
    }

    @Override
    public ManageTypesActivityPresenter provideManageTypesActivityPresenter() {
        return manageTypesActivityPresenter;
    }
}
