package com.wildeastcoders.pantroid.view.fragment;

import android.os.Bundle;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemTypesModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_TYPE_ID;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2017-03-26.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditTypeFragmentTest extends PresenterFragmentTest<EditTypeFragment> {

    public static final long TYPE_ID = 1;
    public static final String TEXT = "TEXT";

    @Mock
    private EditTypeFragmentPresenter presenter;

    @Override
    protected Presenter getPresenterMock() {
        return presenter;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final EditTypeFragment fragment = EditTypeFragment.newInstance();
        fragment.setPantryItemTypesModule(new MockPantryItemTypesModule(presenter));
        setup(fragment);
    }

    @Test
    public void newInstance() throws Exception {
        final EditTypeFragment fragment = EditTypeFragment.newInstance();
        assertNotNull(fragment);
        final Bundle arguments = fragment.getArguments();
        assertNull(arguments);
    }

    @Test
    public void newInstance1() throws Exception {
        final EditTypeFragment fragment = EditTypeFragment.newInstance(TYPE_ID);
        assertNotNull(fragment);
        final Bundle arguments = fragment.getArguments();
        assertNotNull(arguments);
        assertTrue(arguments.containsKey(KEY_EDIT_TYPE_ID));
        assertEquals(TYPE_ID, arguments.getLong(KEY_EDIT_TYPE_ID));
    }

    @Test
    public void onSaveClicked() throws Exception {
        initializeFragment();
        spyFragment.onSaveClicked();
        verify(presenter).onSaveItemClicked(spyFragment.etTypeName.getText().toString());

        spyFragment.etTypeName.setText(TEXT);
        spyFragment.onSaveClicked();
        verify(presenter).onSaveItemClicked(TEXT);
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