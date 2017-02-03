package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.greenrobot.greendao.DaoException;
import org.junit.After;
import org.junit.Assert;
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
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-02-03.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RemoveTypeUsecaseTest {

    private static final DaoException DAO_EXCEPTION = new DaoException("Test Dao Exception");

    @Mock
    private Repository repository;

    private RemoveTypeUsecase removeTypeUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        removeTypeUsecase = new RemoveTypeUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void init() throws Exception {
        final PantryItemType pantryItem = new PantryItemType(null, "name");
        removeTypeUsecase.init(pantryItem);

        assertEquals(pantryItem, removeTypeUsecase.getPantryItemType());

        removeTypeUsecase.init(null);

        assertEquals(null, removeTypeUsecase.getPantryItemType());
    }

    @Test
    public void executeInitialized() throws Exception {
        final PantryItemType pantryItemType = new PantryItemType(null, "name");
        removeTypeUsecase.init(pantryItemType);

        when(repository.removeType(pantryItemType)).thenReturn(Observable.just(null));

        final Observable<PantryItemType> observable = removeTypeUsecase.execute();
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItemType> events = testSubscriber.getOnNextEvents();
        assertNotNull(events);
        Assert.assertEquals(1, events.size());
        Assert.assertEquals(pantryItemType, events.get(0));
    }

    @Test
    public void executeNotInitialized() throws Exception {
        final Observable<PantryItemType> observable = removeTypeUsecase.execute();
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        Assert.assertEquals(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT, testSubscriber.getOnErrorEvents().get(0).getMessage());
    }

    @Test
    public void executeWithException() throws Exception {
        final PantryItemType pantryItemType = new PantryItemType(null, "name");
        removeTypeUsecase.init(pantryItemType);

        when(repository.removeType(pantryItemType)).thenReturn(Observable.error(DAO_EXCEPTION));

        final Observable<PantryItemType> observable = removeTypeUsecase.execute();
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        observable.subscribe(testSubscriber);

        testSubscriber.assertError(DaoException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();

        Assert.assertEquals(1, errors.size());
        Assert.assertEquals(DAO_EXCEPTION, errors.get(0));
    }
}