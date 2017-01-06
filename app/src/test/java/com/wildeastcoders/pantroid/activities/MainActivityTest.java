package com.wildeastcoders.pantroid.activities;

import com.wildeastcoders.pantroid.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Majfrendmartin on 2016-11-13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
@Ignore
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