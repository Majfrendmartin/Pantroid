package com.wildeastcoders.pantroid.view;

import android.content.ComponentName;
import android.content.Intent;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.view.activity.EditItemActivity;
import com.wildeastcoders.pantroid.view.activity.MainActivity;
import com.wildeastcoders.pantroid.view.activity.ManageTypesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2017-02-04.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityViewTest {

    private MainActivity mainActivity;
    private MainActivity spyMainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric
                .buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
        spyMainActivity = Mockito.spy(mainActivity);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void navigateToManageTypesActivity() throws Exception {
        spyMainActivity.navigateToManageTypesActivity();
        ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyMainActivity).startActivity(argumentCaptor.capture());
        final ComponentName component = argumentCaptor.getValue().getComponent();
        assertEquals(new Intent(mainActivity, ManageTypesActivity.class).getComponent(), component);
    }

    @Test
    public void navigateToNewItemActivity() throws Exception {
        spyMainActivity.navigateToNewItemActivity();
        ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyMainActivity).startActivity(argumentCaptor.capture());
        final ComponentName component = argumentCaptor.getValue().getComponent();
        assertEquals(new Intent(mainActivity, EditItemActivity.class).getComponent(), component);
    }

}