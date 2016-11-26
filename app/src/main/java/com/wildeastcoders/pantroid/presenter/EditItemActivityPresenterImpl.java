package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.EditItemActivityView;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImpl extends AbstractPresenter<EditItemActivityView> implements EditItemActivityPresenter {
    @Override
    public void onSaveItemClicked() {
        if (isViewBounded()) {
            getView().handleSaveItemClicked();
        }
    }

    @Override
    public void onBackClicked() {
        if (isViewBounded()) {
            getView().displayBackConfirmation();
        }
    }

    @Override
    public void onBackConfirmed() {
        if (isViewBounded()) {
            getView().performBackNavigation();
        }
    }
}
