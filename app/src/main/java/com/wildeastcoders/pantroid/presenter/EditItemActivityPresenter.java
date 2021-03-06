package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.activity.EditItemActivityView;

/**
 * Created by Majfrendmartin on 2016-11-08.
 */

public interface EditItemActivityPresenter extends Presenter<EditItemActivityView> {

    void onSaveItemClicked();

    void onBackClicked();

    void onHomeClicked();

    void onBackConfirmed();

    void onHomeConfirmed();
}
