package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.event.DialogButtonClickedEvent;
import com.wildeastcoders.pantroid.model.event.SaveItemClickedEvent;
import com.wildeastcoders.pantroid.view.activity.EditItemActivityView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_BACK_DIALOG_ID;
import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_HOME_DIALOG_ID;
import static com.wildeastcoders.pantroid.view.ConfirmationDialogFragment.BUTTON_POSITIVE;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImpl extends AbstractPresenter<EditItemActivityView> implements EditItemActivityPresenter {

    private final EventBus eventBus;

    public EditItemActivityPresenterImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onSaveItemClicked() {
        if (isViewBounded()) {
            eventBus.post(new SaveItemClickedEvent());
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

    @Subscribe
    public void onDialogButtonClickedEvent(@NonNull DialogButtonClickedEvent event) {
        if (event.getButtonId() == BUTTON_POSITIVE) {
            switch (event.getDialogId()) {
                case ABANDON_CHANGES_BACK_DIALOG_ID:
                    onBackConfirmed();
                    break;
                case ABANDON_CHANGES_HOME_DIALOG_ID:
                    onHomeConfirmed();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }
}
