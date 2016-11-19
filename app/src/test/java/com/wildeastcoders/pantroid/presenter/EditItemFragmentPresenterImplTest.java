package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemUsecase;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImplTest {

    public static final String ITEM_NAME = "ITEM_NAME";
    public static final int QUANTITY = 1;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    public static final int ITEM_ID = 1;

    private static final Throwable THROWABLE = new Exception("EXCEPTION");

    @Mock
    private EditItemFragmentView view;

    @Mock
    private PantryItemValidator pantryItemValidator;

    @Mock
    private PantryItemType pantryItemType;

    @Mock
    private PantryItem pantryItem;

    @Mock
    private GetPantryItemUsecase getPantryItemUsecase;

    @Mock
    private GetPantryItemTypesUsecase getPantryItemTypesUsecase;

    private EditItemFragmentPresenter presenter;

    private Bundle bundle;

    private List<PantryItemType> pantryItemTypes;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new EditItemFragmentPresenterImpl(pantryItemValidator, getPantryItemUsecase, getPantryItemTypesUsecase);

        bundle = new Bundle(1);
        bundle.putInt(KEY_EDIT_ITEM_ID, ITEM_ID);

        pantryItemTypes = new ArrayList<>(1);
        pantryItemTypes.add(pantryItemType);

        when(getPantryItemUsecase.execute()).thenReturn(Observable.just(pantryItem));

        when(getPantryItemTypesUsecase.execute()).thenReturn(Observable.just(pantryItemTypes));

        setupItem();
        setupValidatorMock();

        setupRxAndroid();
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

    }

    @Test
    public void onCreateForEditItemViewBounded() throws Exception {
        presenter.bindView(view);
        presenter.onCreate(bundle);

        verify(getPantryItemTypesUsecase).execute();
        verify(getPantryItemUsecase).init(ITEM_ID);
        verify(getPantryItemUsecase).execute();

        verify(view).populateTypesSpinner(pantryItemTypes);
        verify(view).setupNameField(ITEM_NAME);
        verify(view).setupQuantityField(QUANTITY);
        verify(view).setupTypeField(pantryItemType);
        verify(view).setupAddingDateField(ADDING_DATE);
        verify(view).setupBestBeforeField(BEST_BEFORE_DATE);
    }

    @Test
    public void onCreateForEditItemViewNotBounded() throws Exception {
        presenter.onCreate(bundle);

        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(getPantryItemUsecase).execute();

        verify(view, never()).setupNameField(ITEM_NAME);
        verify(view, never()).setupQuantityField(QUANTITY);
        verify(view, never()).setupTypeField(pantryItemType);
        verify(view, never()).setupAddingDateField(ADDING_DATE);
        verify(view, never()).setupBestBeforeField(BEST_BEFORE_DATE);
    }

    @Test
    public void onCreateForEditErrorItemViewBounded() throws Exception {
        presenter.bindView(view);
        presenter.onCreate(bundle);
        when(getPantryItemUsecase.execute()).thenReturn(Observable.error(THROWABLE));

        verify(view).populateTypesSpinner(pantryItemTypes);
        verify(getPantryItemUsecase).execute();

        verify(view).displayErrorDialog();

        verify(view, never()).setupNameField(ITEM_NAME);
        verify(view, never()).setupQuantityField(QUANTITY);
        verify(view, never()).setupTypeField(pantryItemType);
        verify(view, never()).setupAddingDateField(ADDING_DATE);
        verify(view, never()).setupBestBeforeField(BEST_BEFORE_DATE);
    }

    @Test
    public void onCreateForEditItemErrorViewNotBounded() throws Exception {
        presenter.bindView(view);
        presenter.onCreate(bundle);
        when(getPantryItemUsecase.execute()).thenReturn(Observable.error(THROWABLE));

        verify(view, never()).populateTypesSpinner(pantryItemTypes);
        verify(getPantryItemUsecase).execute();

        verify(view, never()).displayErrorDialog();

        verify(view, never()).setupNameField(ITEM_NAME);
        verify(view, never()).setupQuantityField(QUANTITY);
        verify(view, never()).setupTypeField(pantryItemType);
        verify(view, never()).setupAddingDateField(ADDING_DATE);
        verify(view, never()).setupBestBeforeField(BEST_BEFORE_DATE);
    }


    @Test
    public void onSaveItemClicked() throws Exception {

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