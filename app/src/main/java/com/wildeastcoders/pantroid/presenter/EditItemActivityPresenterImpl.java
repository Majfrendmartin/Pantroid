package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.event.SaveItemClickedEvent;
import com.wildeastcoders.pantroid.view.activity.EditItemActivityView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImpl extends AbstractPresenter<EditItemActivityView> implements EditItemActivityPresenter {

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onSaveItemClicked() {
        if (isViewBounded()) {
            EventBus.getDefault().post(new SaveItemClickedEvent());
        }
    }

    @Override
    public void onBackClicked() {
        if (isViewBounded()) {
            getView().displayBackConfirmation();
        }
    }

    @Override
    public void onHomeClicked() {
        if (isViewBounded()) {
            getView().displayHomeConfirmation();
        }
    }

    @Override
    public void onBackConfirmed() {
        if (isViewBounded()) {
            getView().performBackNavigation();
        }
    }

    @Override
    public void onHomeConfirmed() {
        if (isViewBounded()) {
            getView().performHomeNavigation();
        }
    }
}
