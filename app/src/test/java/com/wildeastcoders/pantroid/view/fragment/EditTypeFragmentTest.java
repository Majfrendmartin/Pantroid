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
import com.wildeastcoders.pantroid.utils.TestUtils;

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
import static com.wildeastcoders.pantroid.utils.TestUtils.assertToastDisplayed;
import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_TYPE_ID;
import static com.wildeastcoders.pantroid.view.activity.EditItemActivity.ABANDON_CHANGES_DIALOG_TAG;
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
        assertToastDisplayed(context, R.string.edit_type_validation_error_text);
    }

    @Test
    public void displayDiscardChangesMessage() throws Exception {
        initializeFragment();
        spyFragment.displayDiscardChangesMessage();
        TestUtils.assertConfirmationDialogDisplayed(spyFragment.getResources(),
                spyFragment.getActivity().getSupportFragmentManager(), ABANDON_CHANGES_DIALOG_TAG);
    }

    @Test
    public void displayTypeNotFoundErrorMessage() throws Exception {
        initializeFragment();
        spyFragment.displayTypeNotFoundErrorMessage();
        assertToastDisplayed(context, R.string.edit_type_type_not_found_text);
    }

    @Test
    public void displaySaveSucceedMessage() throws Exception {
        initializeFragment();
        spyFragment.displaySaveSucceedMessage();
        assertToastDisplayed(context, R.string.edit_type_item_saved_successfully);
    }

    @Test
    public void displaySaveFailedMessage() throws Exception {
        initializeFragment();
        spyFragment.displaySaveFailedMessage();
        assertToastDisplayed(context, R.string.edit_type_item_saving_failed);
    }

    @Test
    public void finish() throws Exception {
        initializeFragment();
        spyFragment.finish();
        verify(spyFragment).dismiss();
    }

}