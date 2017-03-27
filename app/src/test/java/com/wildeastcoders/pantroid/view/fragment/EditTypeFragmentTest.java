package com.wildeastcoders.pantroid.view.fragment;

import com.wildeastcoders.pantroid.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;

/**
 * Created by Majfrendmartin on 2017-03-26.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditTypeFragmentTest {
    @Before
    public void setUp() throws Exception {
        final EditTypeFragment fragment = EditTypeFragment.newInstance();
        startFragment(fragment);
        assertNotNull(fragment);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void newInstance() throws Exception {

    }

    @Test
    public void newInstance1() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onCreateView() throws Exception {

    }

    @Test
    public void onSaveClicked() throws Exception {

    }

    @Test
    public void populateTypeDetails() throws Exception {

    }

    @Test
    public void displayValidationError() throws Exception {

    }

    @Test
    public void displayDiscardChangesMessage() throws Exception {

    }

    @Test
    public void displayTypeNotFoundErrorMessage() throws Exception {

    }

    @Test
    public void displaySaveSucceedMessage() throws Exception {

    }

    @Test
    public void displaySaveFailedMessage() throws Exception {

    }

    @Test
    public void finish() throws Exception {

    }

}