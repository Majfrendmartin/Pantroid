package com.wildeastcoders.pantroid.view.fragment;

import android.support.v4.app.Fragment;

import com.wildeastcoders.pantroid.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Majfrendmartin on 2017-03-26.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class PresenterFragmentTest<Y extends Fragment> {

    protected Y spyFragment;

    protected void setup(){

    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onCreateAfterInjection() throws Exception {

    }

    @Test
    public void onDestroy() throws Exception {

    }

    @Test
    public void onResume() throws Exception {

    }

    @Test
    public void onPause() throws Exception {

    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onStop() throws Exception {

    }

    @Test
    public void getApplicationComponent() throws Exception {

    }

    @Test
    public void getActivityModule() throws Exception {

    }
}
