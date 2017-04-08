package com.wildeastcoders.pantroid.view.fragment;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemTypesModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Majfrendmartin on 2017-03-26.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditTypeFragmentTest extends PresenterFragmentTest<EditTypeFragment> {

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