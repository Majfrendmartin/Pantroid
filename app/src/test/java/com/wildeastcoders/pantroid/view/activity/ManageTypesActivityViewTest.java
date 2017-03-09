package com.wildeastcoders.pantroid.view.activity;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 21.02.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ManageTypesActivityViewTest {

    private ManageTypesActivity spyActivity;

    @Mock
    private EditTypeFragment editTypeFragment;

    @Before
    public void setUp() throws Exception {
        final ManageTypesActivity activity = Robolectric
                .buildActivity(ManageTypesActivity.class)
                .create()
                .resume()
                .get();
        spyActivity = Mockito.spy(activity);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void onNavigateBack() throws Exception {
        spyActivity.onNavigateBack();
        verify(spyActivity).onBackPressed();
    }

    @Test
    public void showNewItemTypeDialog() throws Exception {
        when(spyActivity.getEditTypeFragment()).thenReturn(editTypeFragment);
        spyActivity.showNewItemTypeDialog();

        verify(editTypeFragment).show(
                eq(spyActivity.getSupportFragmentManager()),
                eq(ManageTypesActivity.EDIT_TYPE_FRAGMENT_TAG));
    }
}