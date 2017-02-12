package com.wildeastcoders.pantroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;

import com.wildeastcoders.pantroid.PantroidApplication;
import com.wildeastcoders.pantroid.injection.component.ApplicationComponent;
import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.view.View;

import javax.inject.Inject;

/**
 * Created by Majfrendmartin on 2017-02-07.
 */

public abstract class PresenterDialogFragment<T extends Presenter<? extends View>> extends AppCompatDialogFragment {

    @Inject
    T presenter;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Presenter call needs to be done after injection, that's why extra method is required.
     * @param savedInstanceState
     */
    public void onCreateAfterInjection(@Nullable final Bundle savedInstanceState) {
        assert presenter != null;
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @NonNull
    protected ApplicationComponent getApplicationComponent() {
        return ((PantroidApplication) getActivity().getApplication()).getApplicationComponent();
    }

    @NonNull
    protected ActivityModule getActivityModule() {
        return new ActivityModule(getActivity());
    }
}
