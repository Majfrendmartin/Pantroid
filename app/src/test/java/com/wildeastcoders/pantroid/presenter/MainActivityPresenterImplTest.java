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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class MainActivityPresenterImplTest {

    @Mock
    private MainActivityView mainActivityView;
    private MainActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenterImpl();
        presenter.bindView(mainActivityView);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onManageTypesMenuOptionSelected() throws Exception {
        presenter.onManageTypesMenuOptionSelected();
        verify(mainActivityView, times(1)).navigateToManageTypesActivity();
    }

    @Test
    public void onAddNewItemSelected() throws Exception {
        presenter.onAddNewItemSelected();
        verify(mainActivityView, times(1)).navigateToNewItemActivity();
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