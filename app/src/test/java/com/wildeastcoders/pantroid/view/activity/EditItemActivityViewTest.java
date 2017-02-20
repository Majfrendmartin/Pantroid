package com.wildeastcoders.pantroid.view.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.MockPantryItemsModule;
import com.wildeastcoders.pantroid.model.event.DialogButtonClickedEvent;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.view.ConfirmationDialogFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_BACK_DIALOG_ID;
import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_HOME_DIALOG_ID;
import static com.wildeastcoders.pantroid.view.ConfirmationDialogFragment.BUTTON_POSITIVE;
import static com.wildeastcoders.pantroid.view.activity.EditItemActivity.ABANDON_CHANGES_DIALOG_TAG;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-02-15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditItemActivityViewTest {

    public static final int HOME = android.R.id.home;

    @Mock
    private EditItemActivityPresenter presenter;

    @Mock
    private MenuItem menuItem;

    private EditItemActivity spyActivity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final ActivityController<EditItemActivity> activityController = Robolectric
                .buildActivity(EditItemActivity.class);

        final EditItemActivity activity = activityController.get();
        activity.setPantryItemModule(new MockPantryItemsModule(presenter));
        activityController.create()
                .resume()
                .get();
        spyActivity = spy(activity);

        when(menuItem.getItemId()).thenReturn(HOME);
    }

    @Test
    public void displayBackConfirmation() throws Exception {
        spyActivity.displayBackConfirmation();
        verify(spyActivity).getSupportFragmentManager();
        final Fragment fragment = spyActivity.getSupportFragmentManager().findFragmentByTag(ABANDON_CHANGES_DIALOG_TAG);
        assertTrue(fragment instanceof ConfirmationDialogFragment);
    }

    @Test
    public void displayHomeConfirmation() throws Exception {
        spyActivity.displayHomeConfirmation();
        verify(spyActivity).getSupportFragmentManager();
        final Fragment fragment = spyActivity.getSupportFragmentManager().findFragmentByTag(ABANDON_CHANGES_DIALOG_TAG);
        assertTrue(fragment instanceof ConfirmationDialogFragment);
    }

    @Test
    public void handleBackConfirmation() throws Exception {
        spyActivity.onDialogButtonClickedEvent(new DialogButtonClickedEvent(ABANDON_CHANGES_BACK_DIALOG_ID, BUTTON_POSITIVE));
        verify(presenter).onBackConfirmed();
    }

    @Test
    public void handleHomeConfirmation() throws Exception {
        spyActivity.onDialogButtonClickedEvent(new DialogButtonClickedEvent(ABANDON_CHANGES_HOME_DIALOG_ID, BUTTON_POSITIVE));
        verify(presenter).onHomeConfirmed();
    }
    
    @Test
    public void onBackPressed() throws Exception {
        spyActivity.onBackPressed();
        verify(presenter).onBackClicked();
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        spyActivity.onOptionsItemSelected(menuItem);
        verify(presenter).onHomeClicked();
    }
}