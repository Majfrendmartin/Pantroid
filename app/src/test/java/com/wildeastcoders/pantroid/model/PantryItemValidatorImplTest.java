package com.wildeastcoders.pantroid.model;


import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.ValidationResult.INVALID;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 19.12.2016.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class PantryItemValidatorImplTest {
    private static final Long TYPE_ID = 1L;
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final PantryItemType TYPE = new PantryItemType(TYPE_ID, TYPE_NAME);
    private static final Observable<PantryItemType> TYPE_OBSERVABLE = Observable.just(TYPE);
    private PantryItemValidatorImpl pantryItemValidator;

    @Mock
    private Repository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        pantryItemValidator = new PantryItemValidatorImpl(repository);
        when(repository.getTypeById(TYPE_ID)).thenReturn(TYPE_OBSERVABLE);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void validateName() throws Exception {
        final String name1 = "Dżem jabłkowo-truskawkowo-rabarbarowy";
        final String name2 = "             ";
        final String name3 = "Konstantynopolitańczykowianeczka_stółzpowyłamywanyminogami";
        assertEquals(VALID, pantryItemValidator.validateName(name1));
        assertEquals(INVALID, pantryItemValidator.validateName(name2));
        assertEquals(INVALID, pantryItemValidator.validateName(name3));

    }

    @Test
    public void validateType() throws Exception {

    }

    @Test
    public void validateQuantity() throws Exception {

    }

    @Test
    public void validateAddingDate() throws Exception {

    }

    @Test
    public void validateBestBeforeDate() throws Exception {

    }

}