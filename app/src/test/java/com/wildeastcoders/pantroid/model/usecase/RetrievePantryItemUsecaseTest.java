package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-04.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RetrievePantryItemUsecaseTest {

    private static final Long ID = 1L;
    private static final PantryItem PANTRY_ITEM = new PantryItem(ID, "name", 1L, new Date(), new Date(), 1);

    private RetrievePantryItemUsecase retrievePantryItemUsecase;

    @Mock
    Repository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        retrievePantryItemUsecase = new RetrievePantryItemUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void init() throws Exception {
        retrievePantryItemUsecase.init(ID);
        assertEquals(ID, ((RetrievePantryItemUsecaseImpl) retrievePantryItemUsecase).getItemId());
    }

    @Test
    public void execute() throws Exception {
        TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        retrievePantryItemUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertEquals(MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT, errors.get(0).getMessage());

        when(repository.getItemById(ID)).thenReturn(Observable.just(PANTRY_ITEM));

        testSubscriber = new TestSubscriber<>();
        retrievePantryItemUsecase.init(ID);
        retrievePantryItemUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItem> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        assertEquals(PANTRY_ITEM, events.get(0));
    }
}