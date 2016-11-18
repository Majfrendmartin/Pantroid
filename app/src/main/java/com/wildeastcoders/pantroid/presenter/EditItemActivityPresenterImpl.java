package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.view.EditItemActivityView;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImpl extends AbstractPresenter<EditItemActivityView> implements EditItemActivityPresenter {
    @Override
    public void onSaveItemClicked() {
        if (isViewBound()) {
            getView().handleSaveItemClicked();
        }
    }

    @Override
    public void onBackClicked() {
        if (isViewBound()) {
            getView().displayBackConfirmation();
        }
    }

    @Override
    public void onBackConfirmed() {
        if (isViewBound()) {
            getView().performBackNavigation();
        }
    }
}
