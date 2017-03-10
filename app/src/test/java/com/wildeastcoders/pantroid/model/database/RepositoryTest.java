package com.wildeastcoders.pantroid.model.database;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.DaoSession;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemDao;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.PantryItemTypeDao;
import com.wildeastcoders.pantroid.utils.RobolectricRxJavaTestRunner;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.Query;
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
import static org.junit.Assert.assertEquals;

/**
 * Created by Majfrendmartin on 2016-12-14.
 */

@RunWith(RobolectricRxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RepositoryTest {

    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(null, "PANTRY_ITEM_TYPE_0");
    private static final PantryItem PANTRY_ITEM = new PantryItem(null, "PANTRY_ITEM_0", 1L, new Date(), new Date(), 1);
    private Repository repository;
    private DaoSession daoSession;

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

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        List<List<PantryItem>> result = testSubscriber.getOnNextEvents();
        assertEquals(0, result.get(0).size());
        subscription.unsubscribe();

        daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        daoSession.getPantryItemDao().insert(PANTRY_ITEM);
        daoSession.clear();

        observable.subscribe(testSubscriber2);

        testSubscriber2.awaitTerminalEvent();

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

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        List<List<PantryItemType>> result = testSubscriber.getOnNextEvents();
        assertEquals(0, result.get(0).size());
        subscription.unsubscribe();

        daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        daoSession.clear();

        observable.subscribe(testSubscriber2);

        testSubscriber2.awaitTerminalEvent();

        testSubscriber2.assertNoErrors();
        result = testSubscriber2.getOnNextEvents();
        assertEquals(1, result.get(0).size());
    }

    @Test
    public void addItem() throws Exception {
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();

        final long id = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        final PantryItem pantryItem = new PantryItem(null, "Name", id, new Date(), new Date(), 1);

        final Observable<PantryItem> observable = repository.addItem(pantryItem);
        observable.subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        final PantryItem result = testSubscriber.getOnNextEvents().get(0);
        assertEquals(pantryItem, result);

        daoSession.clear();

        final List<PantryItem> dbItems = daoSession.getPantryItemDao().loadAll();
        assertEquals(1, dbItems.size());

        assertEquals(pantryItem, dbItems.get(0));

        daoSession.clear();
        final TestSubscriber<List<PantryItem>> testSubscriber2 = new TestSubscriber<>();
        repository.getItems().subscribe(testSubscriber2);

        testSubscriber2.awaitTerminalEvent();
        testSubscriber2.assertNoErrors();
        final List<List<PantryItem>> getItemsResult = testSubscriber2.getOnNextEvents();
        assertEquals(1, getItemsResult.get(0).size());
        assertEquals(pantryItem, getItemsResult.get(0).get(0));
    }

    @Test
    public void addType() throws Exception {
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();

        final PantryItemType pantryItemType = new PantryItemType(null, "PantryItemType_0");

        final Observable<PantryItemType> observable = repository.addType(pantryItemType);
        observable.subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        final PantryItemType result = testSubscriber.getOnNextEvents().get(0);
        assertEquals(pantryItemType, result);

        daoSession.clear();

        final List<PantryItemType> dbItems = daoSession.getPantryItemTypeDao().loadAll();
        assertEquals(1, dbItems.size());

        assertEquals(pantryItemType, dbItems.get(0));

        daoSession.clear();
        final TestSubscriber<List<PantryItemType>> testSubscriber2 = new TestSubscriber<>();
        repository.getTypes().subscribe(testSubscriber2);

        testSubscriber2.awaitTerminalEvent();
        testSubscriber2.assertNoErrors();
        final List<List<PantryItemType>> getItemsResult = testSubscriber2.getOnNextEvents();
        assertEquals(1, getItemsResult.get(0).size());
        assertEquals(pantryItemType, getItemsResult.get(0).get(0));
    }

    @Test
    public void updateItem() throws Exception {
        final long id1 = daoSession.getPantryItemTypeDao().insert(new PantryItemType(null, "Type1"));

        final PantryItem pantryItem = new PantryItem(null, "Name", id1, new Date(), new Date(), 1);

        final long pantryItemId = daoSession.getPantryItemDao().insert(pantryItem);

        final long id2 = daoSession.getPantryItemTypeDao().insert(new PantryItemType(null, "Type2"));
        final String name1 = "Name1";
        final int quantity = 2;
        final Date date = new Date();

        pantryItem.setName(name1);
        pantryItem.setTypeId(id2);
        pantryItem.setAddingDate(date);
        pantryItem.setBestBeforeDate(date);
        pantryItem.setQuantity(quantity);

        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        repository.updateItem(pantryItem).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        final List<PantryItem> result = testSubscriber.getOnNextEvents();

        assertEquals(1, result.size());
        daoSession.clear();

        final PantryItem newItem = daoSession.getPantryItemDao().load(pantryItemId);
        assertEquals(name1, newItem.getName());
        assertEquals(quantity, newItem.getQuantity());
        assertEquals(date, newItem.getAddingDate());
        assertEquals(date, newItem.getBestBeforeDate());
        assertEquals(id2, newItem.getTypeId().longValue());

        assertEquals(newItem, result.get(0));
    }

    @Test
    public void updateType() throws Exception {
        final PantryItemType pantryItemType = new PantryItemType(null, "Type1");

        final long id1 = daoSession.getPantryItemTypeDao().insert(pantryItemType);
        final String name2 = "Type2";

        pantryItemType.setName(name2);

        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        repository.updateType(pantryItemType).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        final List<PantryItemType> result = testSubscriber.getOnNextEvents();

        assertEquals(1, result.size());
        daoSession.clear();

        final PantryItemType newItem = daoSession.getPantryItemTypeDao().load(id1);
        assertEquals(name2, newItem.getName());

        assertEquals(newItem, result.get(0));
    }

    @Test
    public void removeItem() throws Exception {
        final long typeId = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        final PantryItem pantryItem = new PantryItem(null, "Name", typeId, new Date(), new Date(), 1);
        final PantryItemDao pantryItemDao = daoSession.getPantryItemDao();
        pantryItemDao.insert(pantryItem);

        final Query<PantryItem> query = pantryItemDao.queryBuilder()
                .where(PantryItemDao.Properties.Id.eq(pantryItem.getId())).build();
        final List<PantryItem> result = query.list();

        assertEquals(1, result.size());
        assertEquals(pantryItem, result.get(0));

        final TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        repository.removeItem(pantryItem).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();

        assertEquals(0, query.list().size());
    }

    @Test
    public void removeItemNotExisting() throws Exception {
        final long typeId = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        final PantryItem pantryItem = new PantryItem(null, "Name", typeId, new Date(), new Date(), 1);
        final PantryItemDao pantryItemDao = daoSession.getPantryItemDao();

        final Query<PantryItem> query = pantryItemDao.queryBuilder()
                .where(PantryItemDao.Properties.Id.eq(1)).build();
        final List<PantryItem> result = query.list();

        assertEquals(0, result.size());

        final TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        repository.removeItem(pantryItem).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertError(DaoException.class);
    }

    @Test
    public void removeType() throws Exception {
        final PantryItemType pantryItemType = new PantryItemType(null, "Name1");
        final PantryItemTypeDao pantryItemDao = daoSession.getPantryItemTypeDao();
        pantryItemDao.insert(pantryItemType);

        final Query<PantryItemType> query = pantryItemDao.queryBuilder()
                .where(PantryItemTypeDao.Properties.Id.eq(pantryItemType.getId())).build();
        final List<PantryItemType> result = query.list();

        assertEquals(1, result.size());
        assertEquals(pantryItemType, result.get(0));

        final TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        repository.removeType(pantryItemType).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();

        assertEquals(0, query.list().size());
    }

    @Test
    public void removeTypeNotExisting() throws Exception {
        final PantryItemType pantryItemType = new PantryItemType(null, "Name1");
        final PantryItemTypeDao pantryItemDao = daoSession.getPantryItemTypeDao();

        final Query<PantryItemType> query = pantryItemDao.queryBuilder()
                .where(PantryItemTypeDao.Properties.Id.eq(1)).build();
        final List<PantryItemType> result = query.list();

        assertEquals(0, result.size());

        final TestSubscriber<Void> testSubscriber = new TestSubscriber<>();
        repository.removeType(pantryItemType).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertError(DaoException.class);
    }

    @Test
    public void getTypeById() throws Exception {
        final long id = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);

        daoSession.clear();

        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        repository.getTypeById(id).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();

        final List<PantryItemType> result = testSubscriber.getOnNextEvents();

        assertEquals(1, result.size());
        assertEquals(PANTRY_ITEM_TYPE, result.get(0));
    }

    @Test
    public void getItemById() throws Exception {
        final long typeId = daoSession.getPantryItemTypeDao().insert(PANTRY_ITEM_TYPE);
        final PantryItem pantryItem = new PantryItem(null, "Name", typeId, new Date(), new Date(), 1);
        final PantryItemDao pantryItemDao = daoSession.getPantryItemDao();
        final long id = pantryItemDao.insert(pantryItem);
        daoSession.clear();

        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        repository.getItemById(id).subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();

        final List<PantryItem> result = testSubscriber.getOnNextEvents();

        assertEquals(1, result.size());
        assertEquals(pantryItem, result.get(0));
    }
}