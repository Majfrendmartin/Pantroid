package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.activity.ManageTypesActivityView;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface ManageTypesActivityPresenter extends Presenter<ManageTypesActivityView> {
    void onAddButtonClicked();
    void onBackButtonClicked();
}
