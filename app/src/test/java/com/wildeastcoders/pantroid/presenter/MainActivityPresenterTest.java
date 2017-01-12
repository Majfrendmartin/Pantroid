package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.MainActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class MainActivityPresenterTest {

    @Mock
    private MainActivityView mainActivityView;
    private MainActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenterImpl();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onManageTypesMenuOptionSelected() throws Exception {
        presenter.bindView(mainActivityView);
        presenter.onManageTypesMenuOptionSelected();
        verify(mainActivityView).navigateToManageTypesActivity();
    }

    @Test
    public void onAddNewItemSelected() throws Exception {
        presenter.bindView(mainActivityView);
        presenter.onAddNewItemSelected();
        verify(mainActivityView).navigateToNewItemActivity();
    }

    @Test
    public void onManageTypesMenuOptionSelectedViewNotBounded() throws Exception {
        presenter.onManageTypesMenuOptionSelected();
        verify(mainActivityView, never()).navigateToManageTypesActivity();
    }

    @Test
    public void onAddNewItemSelectedViewNotBounded() throws Exception {
        presenter.onAddNewItemSelected();
        verify(mainActivityView, never()).navigateToNewItemActivity();
    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onStop() throws Exception {

    }

    @Test
    public void onResume() throws Exception {

    }

    @Test
    public void onPause() throws Exception {

    }

    @Test
    public void onSaveInstanceState() throws Exception {

    }
}