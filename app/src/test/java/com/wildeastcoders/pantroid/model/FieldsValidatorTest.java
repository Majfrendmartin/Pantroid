package com.wildeastcoders.pantroid.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.wildeastcoders.pantroid.model.ValidationResult.INVALID;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Majfrendmartin on 19.12.2016.
 */

@RunWith(RobolectricTestRunner.class)
public class FieldsValidatorTest {
    private static final Long TYPE_ID = 1L;
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final PantryItemType TYPE = new PantryItemType(TYPE_ID, TYPE_NAME);
    private FieldsValidatorImpl fieldsValidator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        fieldsValidator = new FieldsValidatorImpl();
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
        assertEquals(VALID, fieldsValidator.validateName(name1));
        assertEquals(INVALID, fieldsValidator.validateName(name2));
        assertEquals(INVALID, fieldsValidator.validateName(name3));
    }

    @Test
    public void validateType() throws Exception {
        assertEquals(VALID, fieldsValidator.validateType(TYPE));
        assertEquals(INVALID, fieldsValidator.validateType(new PantryItemType(-10L, "name")));
        assertEquals(INVALID, fieldsValidator.validateType(new PantryItemType(10L, "    ")));
    }

    @Test
    public void validateQuantity() throws Exception {
        assertEquals(VALID, fieldsValidator.validateQuantity(1));
        assertEquals(INVALID, fieldsValidator.validateQuantity(0));
        assertEquals(INVALID, fieldsValidator.validateQuantity(-1));
    }

    @Test
    public void validateAddingDate() throws Exception {
        assertEquals(VALID, fieldsValidator.validateAddingDate(new Date()));
        assertEquals(INVALID, fieldsValidator.validateAddingDate(null));
    }

    @Test
    public void validateBestBeforeDate() throws Exception {
        final Date past = new GregorianCalendar(1990, 10, 10).getTime();
        final Date now = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        final Date future = calendar.getTime();

        assertEquals(VALID, fieldsValidator.validateBestBeforeDate(now, future));
        assertEquals(INVALID, fieldsValidator.validateBestBeforeDate(now, past));
        assertEquals(INVALID, fieldsValidator.validateBestBeforeDate(now, now));
        assertEquals(INVALID, fieldsValidator.validateBestBeforeDate(null, future));
        assertEquals(INVALID, fieldsValidator.validateBestBeforeDate(now, null));
        assertEquals(INVALID, fieldsValidator.validateBestBeforeDate(null, null));
    }
}