package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.wildeastcoders.pantroid.model.Constants.MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-23.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RetrievePantryItemTypeUsecaseTest {

    private static final Long ID = 1L;
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(ID, "name");

    @Mock
    private Repository repository;

    private RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        retrievePantryItemTypeUsecase = new RetrievePantryItemTypeUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void init() throws Exception {
        retrievePantryItemTypeUsecase.init(ID);
        assertEquals(ID, ((RetrievePantryItemTypeUsecaseImpl) retrievePantryItemTypeUsecase).getItemId());
    }

    @Test
    public void executeNotInitialized() throws Exception {
        TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        retrievePantryItemTypeUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertEquals(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT, errors.get(0).getMessage());
    }

    @Test
    public void executeInitialized() throws Exception {
        when(repository.getTypeById(ID)).thenReturn(Observable.just(PANTRY_ITEM_TYPE));

        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        retrievePantryItemTypeUsecase.init(ID);
        retrievePantryItemTypeUsecase.execute()
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItemType> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        assertEquals(PANTRY_ITEM_TYPE, events.get(0));
    }
}