package com.wildeastcoders.pantroid.activities;

import com.wildeastcoders.pantroid.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import dagger.Component;

import static org.junit.Assert.*;

/**
 * Created by Majfrendmartin on 2016-11-13.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onOptionsItemSelected() throws Exception {

    }

    @Test
    public void navigateToManageTypesActivity() throws Exception {

    }

    @Test
    public void navigateToNewItemActivity() throws Exception {

    }

}