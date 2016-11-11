package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.View;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public abstract class AbstractPresenter<T extends View> implements Presenter<T> {
    private T view;

    @Override
    public T getView() {
        return view;
    }

    @Override
    public boolean isViewBound() {
        return view != null;
    }

    @Override
    public void bindView(final T view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }
}
