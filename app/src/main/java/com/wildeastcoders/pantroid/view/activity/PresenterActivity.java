package com.wildeastcoders.pantroid.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wildeastcoders.pantroid.PantroidApplication;
import com.wildeastcoders.pantroid.injection.component.ApplicationComponent;
import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.view.View;

import javax.inject.Inject;

/**
 * Created by Majfrendmartin on 2017-02-07.
 */

public abstract class PresenterActivity<T extends Presenter<? extends View>> extends AppCompatActivity {

    @Inject
    T presenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Presenter call needs to be done after injection, that's why extra method is required.
     * @param savedInstanceState
     */
    protected void onCreateAfterInjection(@Nullable final Bundle savedInstanceState) {
        assert presenter != null;
        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @NonNull
    protected ApplicationComponent getApplicationComponent() {
        return ((PantroidApplication) getApplication()).getApplicationComponent();
    }

    @NonNull
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
