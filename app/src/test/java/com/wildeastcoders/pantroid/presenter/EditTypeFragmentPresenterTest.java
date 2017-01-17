package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.EditTypeFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import rx.Observable;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-14.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditTypeFragmentPresenterTest {

    private static final Long ITEM_ID = 1L;
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(ITEM_ID, ITEM_NAME);
    public static final Exception DATABASE_EXCEPTION = new Exception("DatabaseException");

    @Mock
    private EditTypeFragmentView editTypeFragmentView;

    @Mock
    private Bundle bundle;

    @Mock
    private SavePantryItemTypeUsecase savePantryItemTypeUsecase;

    @Mock
    private RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;

    private EditTypeFragmentPresenter presenter;

    private void bindView() {
        presenter.bindView(editTypeFragmentView);
    }

    private void setupBundle() {
        when(bundle.getLong(KEY_EDIT_ITEM_ID)).thenReturn(ITEM_ID);
        when(bundle.containsKey(KEY_EDIT_ITEM_ID)).thenReturn(true);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        setupBundle();
        presenter = new EditTypeFragmentPresenterImpl(savePantryItemTypeUsecase, retrievePantryItemTypeUsecase);
        when(retrievePantryItemTypeUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPE));
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPE));
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void onCreateForEditTypeViewBounded() throws Exception {
        bindView();
        presenter.onCreate(bundle);
        verify(retrievePantryItemTypeUsecase).init(ITEM_ID);
        verify(retrievePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView).populateTypeDetails(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onCreateForEditTypeViewNotBounded() throws Exception {
        presenter.onCreate(bundle);
        verify(retrievePantryItemTypeUsecase).init(ITEM_ID);
        verify(retrievePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).populateTypeDetails(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onCreateForEditTypeErrorViewBounded() throws Exception {
        when(retrievePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        bindView();
        presenter.onCreate(bundle);
        verify(retrievePantryItemTypeUsecase).init(ITEM_ID);
        verify(retrievePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).populateTypeDetails(PANTRY_ITEM_TYPE);
        verify(editTypeFragmentView).displayTypeNotFoundErrorMessage();
    }

    @Test
    public void onCreateForEditTypeErrorViewNotBounded() throws Exception {
        when(retrievePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        presenter.onCreate(bundle);
        verify(retrievePantryItemTypeUsecase).init(ITEM_ID);
        verify(retrievePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).populateTypeDetails(PANTRY_ITEM_TYPE);
        verify(editTypeFragmentView, never()).displayTypeNotFoundErrorMessage();
    }

    @Test
    public void onCreateForNewTypeViewBounded() throws Exception {
        bindView();
        presenter.onCreate(null);
        verify(retrievePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView, never()).populateTypeDetails(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onCreateForNewTypeViewNotBounded() throws Exception {
        presenter.onCreate(null);
        verify(retrievePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView, never()).populateTypeDetails(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onSaveItemClickedValidationSucceedForNewItemViewBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationSucceedForNewItemViewNotBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationFailedForNewItemViewBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationFailedForNewItemViewNotBounded() throws Exception {

    }

    @Test
    public void onCancelClickedForNewItemViewBounded() throws Exception {

    }

    @Test
    public void onCancelClickedForNewItemViewNotBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemViewBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemViewNotBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationFailedForEditItemViewBounded() throws Exception {

    }

    @Test
    public void onSaveItemClickedValidationFailedForEditItemViewNotBounded() throws Exception {

    }

    @Test
    public void onCancelClickedForEditItemViewBounded() throws Exception {

    }

    @Test
    public void onCancelClickedForEditItemViewNotBounded() throws Exception {

    }
}