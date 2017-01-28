package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.ManageTypesActivityView;

/**
 * Created by Majfrendmartin on 2017-01-28.
 */
public class ManageTypesActivityPresenterImpl extends AbstractPresenter<ManageTypesActivityView> implements ManageTypesActivityPresenter {
    @Override
    public void onAddButtonClicked() {
        if (isViewBounded()) {
            getView().showNewItemTypeDialog();
        }
    }

    @Override
    public void onBackButtonClicked() {
        if (isViewBounded()) {
            getView().onNavigateBack();
        }
    }
}
