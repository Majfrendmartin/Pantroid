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
    private ActivityController<Y> activityController;

    protected abstract Presenter getPresenterMock();

    protected void setup(@NonNull ActivityController<Y> activityController) {
        this.activityController = activityController;
        spyActivity = spy(getActivity());
    }

    protected Y getActivity() {
        return activityController.get();
    }

    protected void initializeActivity() {
        if (activityController != null) {
            activityController.create().start().resume();
        }

        spyActivity = spy(getActivity());
    }

    @Test
    public void onCreate() throws Exception {
        activityController.create();
        verify(getPresenterMock()).onCreate(anyObject());
    }

    @Test
    public void onDestroy() throws Exception {
        initializeActivity();
        activityController.pause().stop().destroy();
        verify(getPresenterMock()).onDestroy();
    }

    @Test
    public void onResume() throws Exception {
        activityController.create().start().resume();
        verify(getPresenterMock()).onResume();
    }

    @Test
    public void onPause() throws Exception {
        initializeActivity();
        activityController.pause();
        verify(getPresenterMock()).onPause();
    }

    @Test
    public void onStart() throws Exception {
        activityController.create().start();
        verify(getPresenterMock()).onStart();
    }

    @Test
    public void onStop() throws Exception {
        initializeActivity();
        activityController.pause().stop();
        verify(getPresenterMock()).onStop();
    }
}