package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.utils.RobolectricRxJavaTestRunner;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.fragment.EditItemActivityFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.model.ValidationResult.INVALID;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-06.
 */
@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class EditItemFragmentPresenterTest {

    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_NAME_2 = "ITEM_NAME_2";
    public static final int QUANTITY = 1;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    public static final Long ITEM_ID = 1L;
    public static final String ITEM_RETRIEVING_EXCEPTION = "ITEM RETRIEVING EXCEPTION";
    public static final String ITEM_TYPES_RETRIEVING_EXCEPTION = "ITEM TYPES RETRIEVING EXCEPTION";
    public static final String ITEM_SAVING_EXCEPTION = "ITEM TYPES RETRIEVING EXCEPTION";

    @Mock
    private EditItemActivityFragmentView view;

    @Mock
    private PantryItemType pantryItemType;

    @Mock
    private PantryItem pantryItem;

    @Mock
    private FieldsValidator fieldsValidator;

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
    private EventBus eventBus;

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

        setupBundle();

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

    private void setupBundle() {
        when(bundle.getLong(KEY_EDIT_ITEM_ID)).thenReturn(ITEM_ID);
        when(bundle.containsKey(KEY_EDIT_ITEM_ID)).thenReturn(true);
    }

    private void setupPresenter(final RetrievePantryItemUsecase retrievePantryItemUsecase,
                                final RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase) {
        presenter = new EditItemFragmentPresenterImpl(fieldsValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesUsecase, savePantryItemUsecase, eventBus);
    }

    private void setupPresenter(final SavePantryItemUsecase savePantryItemUsecase) {
        presenter = new EditItemFragmentPresenterImpl(fieldsValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesUsecase, savePantryItemUsecase, eventBus);
    }

    private void setupPresenter() {
        presenter = new EditItemFragmentPresenterImpl(fieldsValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesUsecase, savePantryItemUsecase, eventBus);
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
        when(fieldsValidator.validateName(ITEM_NAME)).thenReturn(VALID);
        when(fieldsValidator.validateType(pantryItemType)).thenReturn(VALID);
        when(fieldsValidator.validateQuantity(QUANTITY)).thenReturn(VALID);
        when(fieldsValidator.validateAddingDate(ADDING_DATE)).thenReturn(VALID);
        when(fieldsValidator.validateBestBeforeDate(ADDING_DATE, BEST_BEFORE_DATE)).thenReturn(VALID);
    }


    private void setupInvalidValidatorMock() {
        when(fieldsValidator.validateName(ITEM_NAME)).thenReturn(INVALID);
        when(fieldsValidator.validateType(pantryItemType)).thenReturn(INVALID);
        when(fieldsValidator.validateQuantity(QUANTITY)).thenReturn(INVALID);
        when(fieldsValidator.validateAddingDate(ADDING_DATE)).thenReturn(INVALID);
        when(fieldsValidator.validateBestBeforeDate(ADDING_DATE, BEST_BEFORE_DATE)).thenReturn(INVALID);
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

        verify(retrievePantryItemTypesUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(pantryItemTypes, presenter.getItemTypesCache());
        assertEquals(pantryItem, presenter.getPantryItem());
        assertEquals(PantryItemType.createEmptyType(), presenter.getItemTypesCache().get(0));

        verifyFieldsPopulationMethodsCalled();
    }

    @Test
    public void onCreateForEditItemViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);

        presenter.onCreate(bundle);

        verify(retrievePantryItemTypesUsecase).execute();
        verify(retrievePantryItemUsecase).init(ITEM_ID);
        verify(retrievePantryItemUsecase).execute();

        assertEquals(pantryItemTypes, presenter.getItemTypesCache());
        assertEquals(pantryItem, presenter.getPantryItem());
        assertEquals(PantryItemType.createEmptyType(), presenter.getItemTypesCache().get(0));

        verifyFieldsPopulationMethodsNeverCalled();
    }

    @Test
    public void onCreateForEditErrorItemViewBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);

        presenter.bindView(view);
        presenter.onCreate(bundle);

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
        verify(view).populateTypesSpinner(pantryItemTypes);
    }

    @Test
    public void onCreateForNewItemViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesUsecase);
        presenter.onCreate(null);
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
    }

    @Test
    public void onCreateForNewItemErrorViewBounded() throws Exception {
        presenter = new EditItemFragmentPresenterImpl(fieldsValidator,
                retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase, savePantryItemUsecase, eventBus);
        presenter.bindView(view);
        presenter.onCreate(null);
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(view).displayTypeErrorDialog();
    }

    @Test
    public void onCreateForNewItemErrorViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemUsecase, retrievePantryItemTypesFailingUsecase);
        presenter.onCreate(null);
        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(view, never()).displayTypeErrorDialog();
    }

    @Test
    public void onSaveItemClickedValidationSucceedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        presenter.bindView(view);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verifyValidationMethodsWereCalled();

        verify(savePantryItemUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view).displayPantryItemSavedDialog(pantryItem);
    }

    private void verifyValidationMethodsWereCalled() {
        verify(fieldsValidator).validateName(ITEM_NAME);
        verify(fieldsValidator).validateType(pantryItemType);
        verify(fieldsValidator).validateQuantity(QUANTITY);
        verify(fieldsValidator).validateAddingDate(ADDING_DATE);
        verify(fieldsValidator).validateBestBeforeDate(ADDING_DATE, BEST_BEFORE_DATE);
    }

    @Test
    public void onSaveItemClickedValidationSucceedViewNotBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verifyValidationMethodsWereCalled();

        verify(savePantryItemUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
    }

    private void verifyFieldValidationFailedViewBounded(PantryItemFieldType pantryItemFieldType) throws InterruptedException {
        presenter.bindView(view);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verifyValidationMethodsWereCalled();

        verify(savePantryItemUsecase, never()).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        final Map<PantryItemFieldType, ValidationResult> resultMap = new HashMap<>(1);
        resultMap.put(pantryItemFieldType, INVALID);
        verify(view).displayValidationResults(resultMap);
    }

    @Test
    public void onSaveItemClickedNameValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        when(fieldsValidator.validateName(ITEM_NAME)).thenReturn(INVALID);
        verifyFieldValidationFailedViewBounded(PantryItemFieldType.NAME);
    }

    @Test
    public void onSaveItemClickedTypeValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        when(fieldsValidator.validateType(pantryItemType)).thenReturn(INVALID);
        verifyFieldValidationFailedViewBounded(PantryItemFieldType.TYPE);
    }

    @Test
    public void onSaveItemClickedQuantityValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        when(fieldsValidator.validateQuantity(QUANTITY)).thenReturn(INVALID);
        verifyFieldValidationFailedViewBounded(PantryItemFieldType.QUANTITY);
    }

    @Test
    public void onSaveItemClickedAddingDateValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        when(fieldsValidator.validateAddingDate(ADDING_DATE)).thenReturn(INVALID);
        verifyFieldValidationFailedViewBounded(PantryItemFieldType.ADDING_DATE);
    }

    @Test
    public void onSaveItemClickedBestBeforeDateValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        when(fieldsValidator.validateBestBeforeDate(ADDING_DATE, BEST_BEFORE_DATE)).thenReturn(INVALID);
        verifyFieldValidationFailedViewBounded(PantryItemFieldType.BEST_BEFORE_DATE);
    }

    @Test
    public void onFieldValidationFailedViewNotBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        setupInvalidValidatorMock();
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verifyValidationMethodsWereCalled();

        verify(savePantryItemUsecase, never()).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        final Map<PantryItemFieldType, ValidationResult> resultMap = new HashMap<>(5);
        resultMap.put(PantryItemFieldType.NAME, INVALID);
        resultMap.put(PantryItemFieldType.TYPE, INVALID);
        resultMap.put(PantryItemFieldType.QUANTITY, INVALID);
        resultMap.put(PantryItemFieldType.ADDING_DATE, INVALID);
        resultMap.put(PantryItemFieldType.BEST_BEFORE_DATE, INVALID);
        verify(view, never()).displayValidationResults(resultMap);
    }

    @Test
    public void onAllFieldValidationFailedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        setupInvalidValidatorMock();
        presenter.bindView(view);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verifyValidationMethodsWereCalled();

        verify(savePantryItemUsecase, never()).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        final Map<PantryItemFieldType, ValidationResult> resultMap = new HashMap<>(5);
        resultMap.put(PantryItemFieldType.NAME, INVALID);
        resultMap.put(PantryItemFieldType.TYPE, INVALID);
        resultMap.put(PantryItemFieldType.QUANTITY, INVALID);
        resultMap.put(PantryItemFieldType.ADDING_DATE, INVALID);
        resultMap.put(PantryItemFieldType.BEST_BEFORE_DATE, INVALID);
        verify(view).displayValidationResults(resultMap);
    }

    @Test
    public void onSaveItemClickedExceptionOccursViewBounded() throws Exception {
        setupPresenter(savePantryItemFailingUsecase);
        presenter.bindView(view);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemFailingUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        verify(view).displayDataErrorDialog();
    }

    @Test
    public void onSaveItemClickedExceptionOccursViewNotBounded() throws Exception {
        setupPresenter(savePantryItemFailingUsecase);
        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemFailingUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        verify(view, never()).displayDataErrorDialog();
    }

    @Test
    public void onSaveItemClickedForUpdateViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        final PantryItem item = new PantryItem();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME_2);
        item.setType(pantryItemType);
        item.setAddingDate(ADDING_DATE);
        item.setBestBeforeDate(BEST_BEFORE_DATE);
        presenter.setPantryItem(item);
        presenter.bindView(view);

        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view).displayPantryItemSavedDialog(pantryItem);
        verify(view, never()).displayNothingChangedDialog();
        verify(view, never()).displayDataErrorDialog();
    }

    @Test
    public void onSaveItemClickedForUpdateNothingChangedViewBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        final PantryItem item = new PantryItem();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setType(pantryItemType);
        item.setAddingDate(ADDING_DATE);
        item.setQuantity(QUANTITY);
        item.setBestBeforeDate(BEST_BEFORE_DATE);
        presenter.setPantryItem(item);
        presenter.bindView(view);

        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemUsecase, never()).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        verify(view).displayNothingChangedDialog();
        verify(view, never()).displayDataErrorDialog();
    }

    @Test
    public void onSaveItemClickedForUpdateViewNotBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        final PantryItem item = new PantryItem();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME_2);
        item.setType(pantryItemType);
        item.setAddingDate(ADDING_DATE);
        item.setBestBeforeDate(BEST_BEFORE_DATE);
        presenter.setPantryItem(item);

        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemUsecase).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        verify(view, never()).displayNothingChangedDialog();
        verify(view, never()).displayDataErrorDialog();
    }

    @Test
    public void onSaveItemClickedForUpdateNothingChangedViewNotBounded() throws Exception {
        setupPresenter(savePantryItemUsecase);
        final PantryItem item = new PantryItem();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setType(pantryItemType);
        item.setAddingDate(ADDING_DATE);
        item.setQuantity(QUANTITY);
        item.setBestBeforeDate(BEST_BEFORE_DATE);
        presenter.setPantryItem(item);

        presenter.onSaveItemClicked(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);

        verify(savePantryItemUsecase, never()).init(ITEM_NAME, pantryItemType, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        verify(view, never()).displayPantryItemSavedDialog(pantryItem);
        verify(view, never()).displayNothingChangedDialog();
        verify(view, never()).displayDataErrorDialog();
    }

    @Test
    public void onBackPressedViewBounded() throws Exception {
        setupPresenter();
        presenter.bindView(view);
        presenter.onBackPressed();

        verify(view).displayDiscardChangesDialog();
    }

    @Test
    public void onBackPressedViewNotBounded() throws Exception {
        setupPresenter();
        presenter.onBackPressed();

        verify(view, never()).displayDiscardChangesDialog();
    }

    @Test
    public void handleFinishDialogConfirmButtonClickedViewBounded() throws Exception {
        setupPresenter();
        presenter.bindView(view);
        presenter.handleFinishDialogConfirmButtonClicked();

        verify(view).finish();
    }

    @Test
    public void handleFinishDialogConfirmButtonClickedViewNotBounded() throws Exception {
        setupPresenter();
        presenter.handleFinishDialogConfirmButtonClicked();

        verify(view, never()).finish();
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