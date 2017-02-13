package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.activity.MainActivityView;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface MainActivityPresenter extends Presenter<MainActivityView> {
    void onManageTypesMenuOptionSelected();

    void onAddNewItemSelected();
}
