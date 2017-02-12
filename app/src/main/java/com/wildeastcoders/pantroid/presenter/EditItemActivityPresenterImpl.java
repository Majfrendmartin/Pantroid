package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.event.SaveItemClickedEvent;
import com.wildeastcoders.pantroid.view.activity.EditItemActivityView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImpl extends AbstractPresenter<EditItemActivityView> implements EditItemActivityPresenter {
    @Override
    public void onSaveItemClicked() {
        if (isViewBounded()) {
            EventBus.getDefault().post(new SaveItemClickedEvent());
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
