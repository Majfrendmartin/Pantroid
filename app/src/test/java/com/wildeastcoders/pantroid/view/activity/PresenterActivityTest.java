package com.wildeastcoders.pantroid.view.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.Presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2017-03-14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class PresenterActivityTest<Y extends Activity> {

    protected Y spyActivity;

    @Nullable
    private ActivityController<Y> controller;

    protected abstract Presenter getPresenterMock();

    protected void setup(@NonNull ActivityController<Y> activityController) {
        this.controller = activityController;
        spyActivity = spy(getActivity());
    }

    protected Y getActivity() {
        return controller.get();
    }

    protected void initializeActivity() {
        if (controller != null) {
            controller.create().start().resume();
        }

        spyActivity = spy(getActivity());
    }

    @Test
    public void onCreate() throws Exception {
        controller.create();
        verify(getPresenterMock()).onCreate(anyObject());
    }

    @Test
    public void onDestroy() throws Exception {
        initializeActivity();
        controller.pause().stop().destroy();
        verify(getPresenterMock()).onDestroy();
    }

    @Test
    public void onResume() throws Exception {
        controller.create().start().resume();
        verify(getPresenterMock()).onResume();
    }

    @Test
    public void onPause() throws Exception {
        initializeActivity();
        controller.pause();
        verify(getPresenterMock()).onPause();
    }

    @Test
    public void onStart() throws Exception {
        controller.create().start();
        verify(getPresenterMock()).onStart();
    }

    @Test
    public void onStop() throws Exception {
        initializeActivity();
        controller.pause().stop();
        verify(getPresenterMock()).onStop();
    }
}