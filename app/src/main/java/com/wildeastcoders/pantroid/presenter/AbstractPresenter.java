package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.view.View;

import rx.Subscription;

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
    public boolean isViewBounded() {
        return view != null;
    }

    @Override
    public void bindView(final T view) {
        this.view = view;
    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {

    }

    @Override
    public void onDestroy() {

    }

    protected void cleanupSubscriptions(@NonNull Subscription... subscriptions) {
        for (Subscription subscription : subscriptions) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
