package com.wildeastcoders.pantroid.view.activity;

import android.app.Dialog;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.presenter.Presenter;
import com.wildeastcoders.pantroid.utils.MockPantryItemsModule;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.utils.TestUtils;
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

import static com.wildeastcoders.pantroid.view.activity.EditItemActivity.ABANDON_CHANGES_DIALOG_TAG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-02-15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditItemActivityViewTest extends PresenterActivityTest<EditItemActivity> {

    public static final int HOME = android.R.id.home;

    @Mock
    private MenuItem menuItem;

    @Mock
    private EditItemActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final ActivityController<EditItemActivity> activityController = Robolectric
                .buildActivity(EditItemActivity.class);
        super.setup(activityController);
        final EditItemActivity activity = getActivity();
        activity.setPantryItemModule(new MockPantryItemsModule(presenter));
        when(menuItem.getItemId()).thenReturn(HOME);
    }

    @Test
    public void displayBackConfirmation() throws Exception {
        initializeActivity();
        spyActivity.displayBackConfirmation();
        verify(spyActivity).getSupportFragmentManager();
        TestUtils.assertConfirmationDialogDisplayed(spyActivity.getResources(),
                spyActivity.getSupportFragmentManager(), ABANDON_CHANGES_DIALOG_TAG);
    }


    @Test
    public void displayHomeConfirmation() throws Exception {
        initializeActivity();
        spyActivity.displayHomeConfirmation();
        verify(spyActivity).getSupportFragmentManager();
        TestUtils.assertConfirmationDialogDisplayed(spyActivity.getResources(),
                spyActivity.getSupportFragmentManager(), ABANDON_CHANGES_DIALOG_TAG);
    }
    
    @Test
    public void onBackPressed() throws Exception {
        initializeActivity();
        spyActivity.onBackPressed();
        verify(presenter).onBackClicked();
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        initializeActivity();
        spyActivity.onOptionsItemSelected(menuItem);
        verify(presenter).onHomeClicked();
    }

    @Override
    protected Presenter getPresenterMock() {
        return presenter;
    }
}