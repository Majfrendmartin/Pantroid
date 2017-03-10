package com.wildeastcoders.pantroid.view.activity;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemTypesModule;
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
import org.robolectric.util.ActivityController;

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

    @Mock
    private ManageTypesActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        final ActivityController<ManageTypesActivity> activityController = Robolectric
                .buildActivity(ManageTypesActivity.class);

        final ManageTypesActivity activity = activityController.get();
        activity.setPantryItemTypesModule(new MockPantryItemTypesModule(presenter));
        activityController.create()
                .resume()
                .get();

        spyActivity = Mockito.spy(activity);
    }

    @Test
    public void onFabClicked() throws Exception {
        spyActivity.onFabClicked(null);
        verify(presenter).onAddButtonClicked();
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