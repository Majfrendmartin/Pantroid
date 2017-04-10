package com.wildeastcoders.pantroid.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemTypesModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static com.wildeastcoders.pantroid.model.ValidationResult.INVALID;
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
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(TYPE_ID, TEXT);

    @Mock
    private EditTypeFragmentPresenter presenter;

    private Context context = RuntimeEnvironment.application;

    @Override
    protected Presenter getPresenterMock() {
        return presenter;
    }

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Toast.class);
        MockitoAnnotations.initMocks(this);
        final EditTypeFragment fragment = EditTypeFragment.newInstance();
        createFragment(fragment);
    }

    private void createFragment(EditTypeFragment fragment) {
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
        final EditTypeFragment fragment = EditTypeFragment.newInstance(TYPE_ID);
        createFragment(fragment);
        setup(fragment);

        initializeFragment();

        spyFragment.populateTypeDetails(PANTRY_ITEM_TYPE);
        assertEquals(spyFragment.etTypeName.getText().toString(), TEXT);
    }

    @Mock
    private Toast toast;

    @Test
    public void displayValidationError() throws Exception {
        initializeFragment();
        spyFragment.displayValidationError(INVALID);

        final String textOfLatestToast = ShadowToast.getTextOfLatestToast();
        final String expectedString = context.getString(R.string.edit_type_validation_error_text);
        assertEquals(expectedString, textOfLatestToast);
    }

    @Test
    public void displayDiscardChangesMessage() throws Exception {
        initializeFragment();
        spyFragment.displayDiscardChangesMessage();
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