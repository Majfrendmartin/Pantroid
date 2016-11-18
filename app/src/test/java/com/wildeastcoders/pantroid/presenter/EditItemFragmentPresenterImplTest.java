package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemValidator;
import com.wildeastcoders.pantroid.view.EditItemFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */
public class EditItemFragmentPresenterImplTest {


    public static final String ITEM_NAME = "ITEM_NAME";
    public static final int QUANTITY = 1;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    @Mock
    private EditItemFragmentView editItemFragmentView;

    @Mock
    private PantryItemValidator pantryItemValidator;

    @Mock
    private PantryItemType pantryItemType;

    private EditItemFragmentPresenter editItemFragmentPresenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        editItemFragmentPresenter = new EditItemFragmentPresenterImpl(pantryItemValidator);
        setupValidatorMock();
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