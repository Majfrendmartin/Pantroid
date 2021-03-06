package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.event.DialogButtonClickedEvent;
import com.wildeastcoders.pantroid.view.activity.EditItemActivityView;

import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_BACK_DIALOG_ID;
import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_HOME_DIALOG_ID;
import static com.wildeastcoders.pantroid.view.ConfirmationDialogFragment.BUTTON_POSITIVE;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditItemActivityPresenterTest {

    @Mock
    private EditItemActivityView editItemActivityView;

    @Mock
    private EventBus eventBus;

    private EditItemActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new EditItemActivityPresenterImpl(eventBus);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Ignore("Verifying calls to EventBus needed")
    @Test
    public void onSaveItemClickedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onSaveItemClicked();
    }

    @Test
    public void onBackClickedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onBackClicked();
        verify(editItemActivityView).displayBackConfirmation();
    }

    @Test
    public void onBackClickedViewNotBonded() throws Exception {
        presenter.onBackClicked();
        verify(editItemActivityView, never()).displayBackConfirmation();
    }

    @Test
    public void onHomeClickedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onHomeClicked();
        verify(editItemActivityView).displayHomeConfirmation();
    }

    @Test
    public void onHomeClickedViewNotBonded() throws Exception {
        presenter.onHomeClicked();
        verify(editItemActivityView, never()).displayBackConfirmation();
    }

    @Test
    public void onBackConfirmedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onBackConfirmed();
        verify(editItemActivityView).performBackNavigation();
    }

    @Test
    public void onBackConfirmedViewNotBounded() throws Exception {
        presenter.onBackConfirmed();
        verify(editItemActivityView, never()).performBackNavigation();
    }

    @Test
    public void onHomeConfirmedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onHomeConfirmed();
        verify(editItemActivityView).performHomeNavigation();
    }

    @Test
    public void onHomeConfirmedViewNotBounded() throws Exception {
        presenter.onHomeConfirmed();
        verify(editItemActivityView, never()).performHomeNavigation();
    }

    @Test
    public void handleBackConfirmation() throws Exception {
        final EditItemActivityPresenter spyPresenter = Mockito.spy(presenter);
        ((EditItemActivityPresenterImpl) spyPresenter).onDialogButtonClickedEvent(new DialogButtonClickedEvent(ABANDON_CHANGES_BACK_DIALOG_ID, BUTTON_POSITIVE));
        verify(spyPresenter).onBackConfirmed();
    }

    @Test
    public void handleHomeConfirmation() throws Exception {
        final EditItemActivityPresenter spyPresenter = Mockito.spy(presenter);
        ((EditItemActivityPresenterImpl)spyPresenter).onDialogButtonClickedEvent(new DialogButtonClickedEvent(ABANDON_CHANGES_HOME_DIALOG_ID, BUTTON_POSITIVE));
        verify(spyPresenter).onHomeConfirmed();
    }
}