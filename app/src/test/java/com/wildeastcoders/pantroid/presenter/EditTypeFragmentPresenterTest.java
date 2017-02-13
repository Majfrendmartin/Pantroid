package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import rx.Observable;

import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.model.ValidationResult.INVALID;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;
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

    public static final Exception DATABASE_EXCEPTION = new Exception("DatabaseException");
    public static final String EMPTY_NAME = "";
    private static final Long ITEM_ID = 1L;
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String ITEM_NAME_2 = "ITEM_NAME_2";
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(ITEM_ID, ITEM_NAME);
    private static final PantryItemType PANTRY_ITEM_TYPE_2 = new PantryItemType(ITEM_ID, ITEM_NAME_2);
    @Mock
    private EditTypeFragmentView editTypeFragmentView;

    @Mock
    private Bundle bundle;

    @Mock
    private SavePantryItemTypeUsecase savePantryItemTypeUsecase;

    @Mock
    private RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;

    @Mock
    private FieldsValidator fieldsValidator;

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
        presenter = new EditTypeFragmentPresenterImpl(savePantryItemTypeUsecase, retrievePantryItemTypeUsecase, fieldsValidator);
        when(retrievePantryItemTypeUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPE));
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPE));
        when(fieldsValidator.validateName(ITEM_NAME)).thenReturn(VALID);
        when(fieldsValidator.validateName(ITEM_NAME_2)).thenReturn(VALID);
        when(fieldsValidator.validateName("")).thenReturn(INVALID);
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
        bindView();
        presenter.onSaveItemClicked(ITEM_NAME);
        verify(savePantryItemTypeUsecase).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView).displaySaveSucceedMessage();
        verify(editTypeFragmentView).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForNewItemViewNotBounded() throws Exception {
        presenter.onSaveItemClicked(ITEM_NAME);
        verify(savePantryItemTypeUsecase).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationFailedForNewItemViewBounded() throws Exception {
        bindView();
        presenter.onSaveItemClicked(EMPTY_NAME);
        verify(savePantryItemTypeUsecase, never()).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView).displayValidationError(INVALID);
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationFailedForNewItemViewNotBounded() throws Exception {
        presenter.onSaveItemClicked(EMPTY_NAME);
        verify(savePantryItemTypeUsecase, never()).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView, never()).displayValidationError(INVALID);
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForNewItemSaveFailedViewBounded() throws Exception {
        bindView();
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        presenter.onSaveItemClicked(ITEM_NAME);
        verify(savePantryItemTypeUsecase).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView).displaySaveFailedMessage();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForNewItemSaveFailedViewNotBounded() throws Exception {
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        presenter.onSaveItemClicked(ITEM_NAME);
        verify(savePantryItemTypeUsecase).init(ITEM_NAME);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).displaySaveFailedMessage();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemViewBounded() throws Exception {
        bindView();
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase).init(PANTRY_ITEM_TYPE_2);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView).displaySaveSucceedMessage();
        verify(editTypeFragmentView).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemViewNotBounded() throws Exception {
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase).init(PANTRY_ITEM_TYPE_2);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationFailedForEditItemViewBounded() throws Exception {
        bindView();
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        when(fieldsValidator.validateName(ITEM_NAME_2)).thenReturn(INVALID);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase, never()).init(PANTRY_ITEM_TYPE_2);
        verify(savePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView).displayValidationError(INVALID);
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationFailedForEditItemViewNotBounded() throws Exception {
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        when(fieldsValidator.validateName(ITEM_NAME_2)).thenReturn(INVALID);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase, never()).init(PANTRY_ITEM_TYPE_2);
        verify(savePantryItemTypeUsecase, never()).execute();
        verify(editTypeFragmentView, never()).displayValidationError(INVALID);
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemSaveFailedViewBounded() throws Exception {
        bindView();
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase).init(PANTRY_ITEM_TYPE_2);
        verify(savePantryItemTypeUsecase).execute();
        verify(editTypeFragmentView).displaySaveFailedMessage();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onSaveItemClickedValidationSucceedForEditItemSaveFailedViewNotBounded() throws Exception {
        when(savePantryItemTypeUsecase.execute()).thenReturn(Observable.error(DATABASE_EXCEPTION));
        ((EditTypeFragmentPresenterImpl) presenter).setPantryItemType(PANTRY_ITEM_TYPE);
        presenter.onSaveItemClicked(ITEM_NAME_2);
        verify(savePantryItemTypeUsecase).init(PANTRY_ITEM_TYPE_2);
        verify(editTypeFragmentView, never()).displaySaveFailedMessage();
        verify(editTypeFragmentView, never()).displaySaveSucceedMessage();
        verify(editTypeFragmentView, never()).finish();
    }

    @Test
    public void onCancelClickedForNewItemViewBounded() throws Exception {
        bindView();
        presenter.onCancelClicked();
        verify(editTypeFragmentView).displayDiscardChangesMessage();
    }

    @Test
    public void onCancelClickedForNewItemViewNotBounded() throws Exception {
        presenter.onCancelClicked();
        verify(editTypeFragmentView, never()).displayDiscardChangesMessage();
    }

    @Test
    public void onFinishClickedForNewItemViewBounded() throws Exception {
        bindView();
        presenter.onFinishClicked();
        verify(editTypeFragmentView).finish();
    }

    @Test
    public void onFinishClickedForNewItemViewNotBounded() throws Exception {
        bindView();
        presenter.onFinishClicked();
        verify(editTypeFragmentView).finish();
    }
}