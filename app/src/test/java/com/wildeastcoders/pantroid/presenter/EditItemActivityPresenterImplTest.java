package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.EditItemActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemActivityPresenterImplTest {

    @Mock
    private EditItemActivityView editItemActivityView;

    private EditItemActivityPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new EditItemActivityPresenterImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onSaveItemClickedViewBounded() throws Exception {
        presenter.bindView(editItemActivityView);
        presenter.onSaveItemClicked();
        verify(editItemActivityView).handleSaveItemClicked();
    }

    @Test
    public void onSaveItemClickedViewNotBounded() throws Exception {
        presenter.onSaveItemClicked();
        verify(editItemActivityView, never()).handleSaveItemClicked();
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