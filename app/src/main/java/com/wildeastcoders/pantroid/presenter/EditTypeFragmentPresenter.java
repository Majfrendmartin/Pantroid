package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.EditTypeFragmentView;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditTypeFragmentPresenter extends Presenter<EditTypeFragmentView> {
    void onSaveItemClicked(String name);
}
