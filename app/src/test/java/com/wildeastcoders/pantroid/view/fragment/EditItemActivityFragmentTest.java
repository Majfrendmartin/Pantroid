package com.wildeastcoders.pantroid.view.fragment;

import android.app.Application;
import android.os.Bundle;
import android.widget.Spinner;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemTypesModule;
import com.wildeastcoders.pantroid.utils.MockPantryItemsModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_ITEM_ID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 15.04.2017.
 */
public class EditItemActivityFragmentTest extends PresenterFragmentTest<EditItemActivityFragment> {

    private static final long ITEM_ID = 1L;
    private static final long LIST_SIZE = 10;
    private static final List<PantryItemType> PANTRY_ITEM_TYPES = new ArrayList<PantryItemType>() {{
        add(PantryItemType.createEmptyType());
        for (long i = 1; i <= LIST_SIZE; i++) {
            add(new PantryItemType(i, "PANTRY_ITEM_TYPE_" + i));
        }
    }};

    @Mock
    private EditItemFragmentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final EditItemActivityFragment fragment = EditItemActivityFragment.newInstance();
        fragment.setPantryItemTypesModule(new MockPantryItemTypesModule(presenter));
        fragment.setPantryItemsModule(new MockPantryItemsModule(presenter));
        setup(fragment);
    }

    @Override
    protected Presenter getPresenterMock() {
        return presenter;
    }

    @Test
    public void newInstance() throws Exception {
        final EditItemActivityFragment fragment = EditItemActivityFragment.newInstance();
        assertNotNull(fragment);
        final Bundle arguments = fragment.getArguments();
        assertNull(arguments);
    }

    @Test
    public void newInstanceWithItemId() throws Exception {
        final EditItemActivityFragment fragment = EditItemActivityFragment.newInstance(ITEM_ID);
        assertNotNull(fragment);
        final Bundle arguments = fragment.getArguments();
        assertNotNull(arguments);
        assertTrue(arguments.containsKey(KEY_EDIT_ITEM_ID));
        assertEquals(ITEM_ID, arguments.getLong(KEY_EDIT_ITEM_ID));
    }

    @Test
    public void populateTypesSpinner() throws Exception {
        initializeFragment();
        spyFragment.populateTypesSpinner(PANTRY_ITEM_TYPES);
        final Spinner spItemType = spyFragment.spItemType;
        assertNotNull(spItemType);
        final int count = spItemType.getCount();
        assertEquals(PANTRY_ITEM_TYPES.size(), count);
        final Object selectedItem = spItemType.getSelectedItem();
        assertNotNull(selectedItem);
        assertEquals(PANTRY_ITEM_TYPES.get(0), selectedItem);
        for (int i = 0; i < count; i++) {
            assertEquals(PANTRY_ITEM_TYPES.get(i), spItemType.getItemAtPosition(i));
        }
    }

    @Test
    public void setupNameField() throws Exception {

    }

    @Test
    public void setupQuantityField() throws Exception {

    }

    @Test
    public void setupAddingDateField() throws Exception {

    }

    @Test
    public void setupBestBeforeField() throws Exception {

    }

    @Test
    public void setupTypeField() throws Exception {

    }

    @Test
    public void displayTypeErrorDialog() throws Exception {

    }

    @Test
    public void displayDataErrorDialog() throws Exception {

    }

    @Test
    public void displayPantryItemSavedDialog() throws Exception {

    }

    @Test
    public void displayNothingChangedDialog() throws Exception {

    }

    @Test
    public void displayValidationResults() throws Exception {

    }

    @Test
    public void displayDiscardChangesDialog() throws Exception {

    }

    @Test
    public void requestInputDataOnSaveItemClicked() throws Exception {

    }

    @Test
    public void finish() throws Exception {

    }

}