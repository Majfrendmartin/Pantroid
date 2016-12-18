package com.wildeastcoders.pantroid.model.database;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.DaoSession;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.greenrobot.greendao.rx.RxDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.waitForAsyncOperationCompleted;
import static org.junit.Assert.assertEquals;

/**
 * Created by Majfrendmartin on 2016-12-14.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryImplTest {

    private Repository repository;

    private DaoSession daoSession;


    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(null, "PANTRY_ITEM_TYPE_0");
    private static final PantryItem PANTRY_ITEM = new PantryItem(null, "PANTRY_ITEM_0", 1L, new Date(), new Date(), 1);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        daoSession = DbUtils.getDaoSession(RuntimeEnvironment.application, BuildConfig.DB_NAME);

        repository = new RepositoryImpl(daoSession);

    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void getItems() throws Exception {
        final Observable<List<PantryItem>> observable = repository.getItems();

        final TestSubscriber<List<PantryItem>> testSubscriber = new TestSubscriber<>();
        final TestSubscriber<List<PantryItem>> testSubscriber2 = new TestSubscriber<>();
        final Subscription subscription = observable.subscribe(testSubscriber);

        waitForAsyncOperationCompleted();

        testSubscriber.assertNoErrors();
        List<List<PantryItem>> result = testSubscriber.getOnNextEvents();
        assertEquals(0, result.get(0).size());
        subscription.unsubscribe();

        daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        daoSession.getPantryItemDao().insert(PANTRY_ITEM);
        daoSession.clear();

        observable.subscribe(testSubscriber2);

        waitForAsyncOperationCompleted();

        testSubscriber2.assertNoErrors();
        result = testSubscriber2.getOnNextEvents();
        assertEquals(1, result.get(0).size());
    }

    @Test
    public void getTypes() throws Exception {
        final Observable<List<PantryItemType>> observable = repository.getTypes();

        final TestSubscriber<List<PantryItemType>> testSubscriber = new TestSubscriber<>();
        final TestSubscriber<List<PantryItemType>> testSubscriber2 = new TestSubscriber<>();
        final Subscription subscription = observable.subscribe(testSubscriber);

        waitForAsyncOperationCompleted();

        testSubscriber.assertNoErrors();
        List<List<PantryItemType>> result = testSubscriber.getOnNextEvents();
        assertEquals(0, result.get(0).size());
        subscription.unsubscribe();

        daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        daoSession.clear();

        observable.subscribe(testSubscriber2);

        waitForAsyncOperationCompleted();

        testSubscriber2.assertNoErrors();
        result = testSubscriber2.getOnNextEvents();
        assertEquals(1, result.get(0).size());
    }

    @Test
    public void addItem() throws Exception {
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();

        long id = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        final PantryItem pantryItem = new PantryItem(null, "Name", id, new Date(), new Date(), 1);

        final Observable<PantryItem> observable = repository.addItem(pantryItem);
        observable.subscribe(testSubscriber);

        waitForAsyncOperationCompleted();

        testSubscriber.assertNoErrors();
        final PantryItem result = testSubscriber.getOnNextEvents().get(0);
        assertEquals(pantryItem, result);

        daoSession.clear();

        final List<PantryItem> dbItems = daoSession.getPantryItemDao().loadAll();
        assertEquals(1, dbItems.size());

        assertEquals(pantryItem, dbItems.get(0));
    }

    @Test
    public void addType() throws Exception {

    }

    @Test
    public void updateItem() throws Exception {

    }

    @Test
    public void updateType() throws Exception {

    }

    @Test
    public void removeItem() throws Exception {

    }

    @Test
    public void removeType() throws Exception {

    }

}