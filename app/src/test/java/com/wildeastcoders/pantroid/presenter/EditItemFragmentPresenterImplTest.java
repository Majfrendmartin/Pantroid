package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.waitForAsyncOperationCompleted;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditItemFragmentPresenterImplTest {

    public static final String ITEM_NAME = "ITEM_NAME";
    public static final int QUANTITY = 1;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    public static final int ITEM_ID = 1;
    public static final String ITEM_RETRIEVING_EXCEPTION = "ITEM RETRIEVING EXCEPTION";
    public static final String ITEM_TYPES_RETRIEVING_EXCEPTION = "ITEM TYPES RETRIEVING EXCEPTION";
    public static final String ITEM_SAVING_EXCEPTION = "ITEM TYPES RETRIEVING EXCEPTION";

    @Mock
    private EditItemFragmentView view;

    @Mock
    private PantryItemValidator pantryItemValidator;

    @Mock
    private PantryItemType pantryItemType;

    @Mock
    private PantryItem pantryItem;

    @Mock
    private RetrievePantryItemUsecase retrievePantryItemUsecase;

    @Mock
    private RetrievePantryItemUsecase retrievePantryItemFailingUsecase;

    @Mock
    private RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase;

    @Mock
    private RetrievePantryItemTypesUsecase retrievePantryItemTypesFailingUsecase;

    @Mock
    private SavePantryItemUsecase savePantryItemUsecase;

    @Mock
    private SavePantryItemUsecase savePantryItemFailingUsecase;

    private EditItemFragmentPresenter presenter;

    @Mock
    private Bundle bundle;

    private List<PantryItemType> pantryItemTypes;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();

        when(bundle.getInt(KEY_EDIT_ITEM_ID)).thenReturn(ITEM_ID);
        when(bundle.containsKey(KEY_EDIT_ITEM_ID)).thenReturn(true);

        pantryItemTypes = new ArrayList<>(1);
        pantryItemTypes.add(pantryItemType);

        setupItem();
        setupValidatorMock();

        when(retrievePantryItemUsecase.execute()).thenReturn(Observable.just(pantryItem));
        when(retrievePantryItemTypesUsecase.execute()).thenReturn(Observable.just(pantryItemTypes));
        when(savePantryItemUsecase.execute()).thenReturn(Observable.just(pantryItem));
        when(retrievePantryItemFailingUsecase.execute()).thenReturn(Observable.error(new Throwable(ITEM_RETRIEVING_EXCEPTION)));
        when(retrievePantryItemTypesFailingUsecase.execute()).thenReturn(Observable.error(new Throwable(ITEM_TYPES_RETRIEVING_EXCEPTION)));
        when(savePantryItemFailingUsecase.execute()).thenReturn(Observable.error(new Throwable(ITEM_SAVING_EXCEPTION)));
    }

    private void setupPresenter(final RetrievePantryItemUsecase retrievePantryItemUsecase,
                                final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase) {
        presenter = new EditItemFragmentPresenterImpl(pantryItemValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesUsecase, savePantryItemUsecase);
    }

    private void setupPresenter(final SavePantryItemUsecase savePantryItemUsecase) {
        presenter = new EditItemFragmentPresenterImpl(pantryItemValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesUsecase, savePantryItemUsecase);
    }

    private void setupItem() {
        when(pantryItem.getId()).thenReturn(ITEM_ID);
        when(pantryItem.getName()).thenReturn(ITEM_NAME);
        when(pantryItem.getType()).thenReturn(pantryItemType);
        when(pantryItem.getAddingDate()).thenReturn(ADDING_DATE);
        when(pantryItem.getBestBeforeDate()).thenReturn(BEST_BEFORE_DATE);
        when(pantryItem.getQuantity()).thenReturn(QUANTITY);
    }

    private void setupValidatorMock() {
        when(pantryItemValidator.validateName(ITEM_NAME)).thenReturn(true);
        when(pantryItemValidator.validateType(pantryItemType)).thenReturn(true);
        when(pantryItemValidator.validateQuantity(QUANTITY)).thenReturn(true);
        when(pantryItemValidator.validateAddingDate(ADDING_DATE)).thenReturn(true);
        when(pantryItemValidator.validateBestBeforeDate(BEST_BEFORE_DATE)).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    private void verifyFieldsPopulationMethodsNeverCalled() {
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(view, never()).setupNameField(ITEM_NAME);
        verify(view, never()).setupQuantityField(QUANTITY);
        verify(view, never()).setupTypeField(pantryItemType);
        verify(view, never()).setupAddingDateField(ADDING_DATE);
        verify(view, never()).setupBestBeforeField(BEST_BEFORE_DATE);
    }

    private void verifyFieldsPopulationMethodsCalled() {
        verify(view).populateTypesSpinner(pantryItemTypes);
        verify(view).setupNameField(ITEM_NAME);
        verify(view).setupQuantityField(QUANTITY);
        verify(view).setupTypeField(pantryItemType);
        verify(view).setupAddingDateField(ADDING_DATE);
        verify(view).setupBestBeforeField(BEST_BEFORE_DATE);
    }

    @Test
    public void onCreateForEditItemViewBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);

        presenter.bindView(view);
        presenter.onCreate(bundle);

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemTypesUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(pantryItemTypes, presenter.getItemTypesCache());
        assertEquals(pantryItem, presenter.getPantryItem());

        verifyFieldsPopulationMethodsCalled();
    }

    @Test
    public void onCreateForEditItemViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);

        presenter.onCreate(bundle);

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemTypesUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(pantryItemTypes, presenter.getItemTypesCache());
        assertEquals(pantryItem, presenter.getPantryItem());

        verifyFieldsPopulationMethodsNeverCalled();
    }

    @Test
    public void onCreateForEditErrorItemViewBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);

        presenter.bindView(view);
        presenter.onCreate(bundle);

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemTypesFailingUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(null, presenter.getItemTypesCache());
        assertEquals(null, presenter.getPantryItem());

        verify(view).displayTypeErrorDialog();
        verifyFieldsPopulationMethodsNeverCalled();
    }

    @Test
    public void onCreateForEditItemErrorViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);

        presenter.onCreate(bundle);

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemTypesFailingUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(null, presenter.getItemTypesCache());
        assertEquals(null, presenter.getPantryItem());

        verify(view, never()).displayTypeErrorDialog();
        verifyFieldsPopulationMethodsNeverCalled();
    }

    @Test
    public void onCreateForNewItemViewBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);
        presenter.bindView(view);
        presenter.onCreate(null);
        waitForAsyncOperationCompleted();
        verify(view).populateTypesSpinner(pantryItemTypes);
    }

    @Test
    public void onCreateForNewItemViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);
        presenter.onCreate(null);
        waitForAsyncOperationCompleted();
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
    }

    @Test
    public void onCreateForNewItemErrorViewBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);
        presenter.bindView(view);
        presenter.onCreate(null);
        waitForAsyncOperationCompleted();
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(view).displayTypeErrorDialog();
    }

    @Test
    public void onCreateForNewItemErrorViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);
        presenter.onCreate(null);
        waitForAsyncOperationCompleted();
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(view, never()).displayTypeErrorDialog();
    }

    @Test
    public void onSaveItemClickedValidationSucceedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        presenter.bindView(view);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        waitForAsyncOperationCompleted();
        verify(pantryItemValidator).validateName(ITEM_NAME);
        verify(pantryItemValidator).validateType(pantryItemType);
        verify(pantryItemValidator).validateQuantity(QUANTITY);
        verify(pantryItemValidator).validateAddingDate(ADDING_DATE);
        verify(pantryItemValidator).validateBestBeforeDate(BEST_BEFORE_DATE);

        final PantryItem createdItem = presenter.getPantryItem();

        verify(savePantryItemUsecase).init(createdItem);
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