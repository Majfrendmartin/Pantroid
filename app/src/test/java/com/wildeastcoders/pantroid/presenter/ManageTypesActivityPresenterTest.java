package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.view.activity.ManageTypesActivityView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Majfrendmartin on 2017-01-28.
 */
public class ManageTypesActivityPresenterTest {

    @Mock
    private ManageTypesActivityView manageTypesActivityView;

    private ManageTypesActivityPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ManageTypesActivityPresenterImpl();
    }

    @Test
    public void onAddButtonClickedViewBounded() throws Exception {
        presenter.bindView(manageTypesActivityView);
        presenter.onAddButtonClicked();
        verify(manageTypesActivityView).showNewItemTypeDialog();
    }

    @Test
    public void onBackButtonClickedViewBounded() throws Exception {
        presenter.bindView(manageTypesActivityView);
        presenter.onBackButtonClicked();
        verify(manageTypesActivityView).onNavigateBack();
    }

    @Test
    public void onAddButtonClickedViewNotBounded() throws Exception {
        presenter.onAddButtonClicked();
        verify(manageTypesActivityView, never()).showNewItemTypeDialog();
    }

    @Test
    public void onBackButtonClickedViewNotBounded() throws Exception {
        presenter.onBackButtonClicked();
        verify(manageTypesActivityView, never()).onNavigateBack();
    }

}