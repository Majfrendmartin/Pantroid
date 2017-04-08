package com.wildeastcoders.pantroid.view.fragment;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.Presenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-03-26.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class PresenterFragmentTest<Y extends Fragment> {

    protected Y spyFragment;
    private SupportFragmentController<Y> controller;
    @Mock
    protected Dialog dialog;

    protected void setup(Y fragment) {
        spyFragment = spy(fragment);
        if (spyFragment instanceof DialogFragment) {
            when(((DialogFragment) spyFragment).getDialog()).thenReturn(dialog);
        }
        controller = SupportFragmentController.of(spyFragment);
    }

    public Y getFragment() {
        return spyFragment;
    }

    protected abstract Presenter getPresenterMock();

    protected void initializeFragment() {
        controller.create().start().resume();
    }

    @Test
    public void onCreate() throws Exception {
        controller.create();
        verify(getPresenterMock()).onCreate(anyObject());
    }

    @Test
    public void onDestroy() throws Exception {
        initializeFragment();
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
        initializeFragment();
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
        initializeFragment();
        controller.pause().stop();
        verify(getPresenterMock()).onStop();
    }
}
