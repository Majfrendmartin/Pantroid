package com.wildeastcoders.pantroid.view.activity;

import android.content.ComponentName;
import android.content.Intent;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemsModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2017-02-04.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityViewTest extends PresenterActivityTest<MainActivity> {

    @Mock
    private MainActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final ActivityController<MainActivity> activityController = Robolectric
                .buildActivity(MainActivity.class);
        super.setup(activityController);
        final MainActivity activity = getActivity();
        activity.setPantryItemModule(new MockPantryItemsModule(presenter));
    }

    @Test
    public void navigateToManageTypesActivity() throws Exception {
        initializeActivity();
        spyActivity.navigateToManageTypesActivity();
        ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyActivity).startActivity(argumentCaptor.capture());
        final ComponentName component = argumentCaptor.getValue().getComponent();
        assertEquals(new Intent(spyActivity, ManageTypesActivity.class).getComponent(), component);
    }

    @Test
    public void navigateToNewItemActivity() throws Exception {
        initializeActivity();
        spyActivity.navigateToNewItemActivity();
        final ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(spyActivity).startActivity(argumentCaptor.capture());
        final ComponentName component = argumentCaptor.getValue().getComponent();
        assertEquals(new Intent(spyActivity, EditItemActivity.class).getComponent(), component);
    }

    @Override
    protected Presenter getPresenterMock() {
        return presenter;
    }


}