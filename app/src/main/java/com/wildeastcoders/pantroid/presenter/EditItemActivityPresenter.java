package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.EditItemActivityView;

/**
 * Created by Majfrendmartin on 2016-11-08.
 */

public interface EditItemActivityPresenter extends Presenter<EditItemActivityView> {

    void onSaveItemClicked();

    void onBackClicked();

    void onBackConfirmed();
}
