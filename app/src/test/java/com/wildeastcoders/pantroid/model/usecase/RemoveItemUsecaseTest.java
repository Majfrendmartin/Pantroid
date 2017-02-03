package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.greenrobot.greendao.DaoException;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RemoveItemUsecaseTest {

    private static final DaoException DAO_EXCEPTION = new DaoException("Test Dao Exception");

    @Mock
    private Repository repository;

    private RemoveItemUsecase removeItemUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        removeItemUsecase = new RemoveItemUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void init() throws Exception {
        final PantryItem pantryItem = new PantryItem(null, "name", 1L, new Date(), new Date(), 1);
        removeItemUsecase.init(pantryItem);

        assertEquals(pantryItem, removeItemUsecase.getPantryItem());

        removeItemUsecase.init(null);

        assertEquals(null, removeItemUsecase.getPantryItem());
    }

    @Test
    public void executeInitialized() throws Exception {
        final PantryItem pantryItem = new PantryItem(null, "name", 1L, new Date(), new Date(), 1);
        removeItemUsecase.init(pantryItem);

        when(repository.removeItem(pantryItem)).thenReturn(Observable.just(null));

        final Observable<PantryItem> observable = removeItemUsecase.execute();
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItem> events = testSubscriber.getOnNextEvents();
        assertNotNull(events);
        assertEquals(1, events.size());
        assertEquals(pantryItem, events.get(0));
    }

    @Test
    public void executeNotInitialized() throws Exception {
        final Observable<PantryItem> observable = removeItemUsecase.execute();
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        assertEquals(MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT, testSubscriber.getOnErrorEvents().get(0).getMessage());
    }

    @Test
    public void executeWithException() throws Exception {
        final PantryItem pantryItem = new PantryItem(null, "name", 1L, new Date(), new Date(), 1);
        removeItemUsecase.init(pantryItem);

        when(repository.removeItem(pantryItem)).thenReturn(Observable.error(DAO_EXCEPTION));

        final Observable<PantryItem> observable = removeItemUsecase.execute();
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertError(DaoException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();

        assertEquals(1, errors.size());
        assertEquals(DAO_EXCEPTION, errors.get(0));
    }
}