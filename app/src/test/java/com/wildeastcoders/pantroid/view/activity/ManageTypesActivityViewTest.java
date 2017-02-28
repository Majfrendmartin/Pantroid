package com.wildeastcoders.pantroid.view.activity;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.view.ViewUtils;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Created by Majfrendmartin on 21.02.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest(ViewUtils.class)
public class ManageTypesActivityViewTest {

    private ManageTypesActivity spyActivity;

    @Before
    public void setUp() throws Exception {
        final ManageTypesActivity activity = Robolectric
                .buildActivity(ManageTypesActivity.class)
                .create()
                .resume()
                .get();
        spyActivity = Mockito.spy(activity);
        PowerMockito.spy(ViewUtils.class);

//        PowerMockito.doNothing().when(ViewUtils.class);
//        ViewUtils.showDialogFragment(anyObject(),
//                spyActivity.getSupportFragmentManager(),
//                ManageTypesActivity.EDIT_TYPE_FRAGMENT_TAG);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onNavigateBack() throws Exception {
        spyActivity.onNavigateBack();
        verify(spyActivity).onBackPressed();
    }

//    @Test
//    public void showNewItemTypeDialog() throws Exception {
//        final DialogFragment fragment = spyActivity.showNewItemTypeDialog();
//        verifyStatic(times(1));
//        ViewUtils.showDialogFragment(anyObject(),
//                eq(spyActivity.getSupportFragmentManager()),
//                eq(ManageTypesActivity.EDIT_TYPE_FRAGMENT_TAG));
//    }

}