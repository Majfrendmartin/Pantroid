package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
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

/**
 * Created by Majfrendmartin on 2017-01-08.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class SavePantryItemUsecaseTest {

    public static final String NAME = "";
    public static final Long TYPE_ID = 1L;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    public static final int QUANTITY = 1;
    public static final Long ID = 1L;
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(1L, "TYPE_NAME");
    private static final PantryItem PANTRY_ITEM = new PantryItem(null, NAME, TYPE_ID, ADDING_DATE, BEST_BEFORE_DATE, QUANTITY);

    static {
        PANTRY_ITEM.setType(PANTRY_ITEM_TYPE);
    }

    private SavePantryItemUsecase savePantryItemUsecase;

    @Mock
    private Repository repository;

    private static Observable<PantryItem> createSavedItemObservable() {
        final PantryItem item = new PantryItem(PANTRY_ITEM);
        item.setId(ID);
        return Observable.just(item);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        savePantryItemUsecase = new SavePantryItemUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    private void assertPantryItemEquals(final PantryItem item) {
        assertEquals(NAME, item.getName());
        assertEquals(TYPE_ID, item.getTypeId());
        assertEquals(QUANTITY, item.getQuantity());
        assertEquals(ADDING_DATE, item.getAddingDate());
        assertEquals(BEST_BEFORE_DATE, item.getBestBeforeDate());
    }

    private PantryItem getItem() {
        return ((SavePantryItemUsecaseImpl) savePantryItemUsecase).getItem();
    }

    @Test
    public void initWithObject() throws Exception {
        savePantryItemUsecase.init(PANTRY_ITEM);
        assertPantryItemEquals(getItem());
    }

    @Test
    public void initWithParameters() throws Exception {
        savePantryItemUsecase.init(NAME, PANTRY_ITEM_TYPE, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        assertPantryItemEquals(getItem());
    }

    @Test
    public void executeNotInitialized() throws Exception {
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        savePantryItemUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertEquals(MISSING_PANTRY_ITEM_OBJECT_ERROR_TEXT, errors.get(0).getMessage());
    }

    @Test
    public void executeInitializedByObject() throws Exception {
        savePantryItemUsecase.init(PANTRY_ITEM);
        execute();
    }

    @Test
    public void executeInitializedByParameters() throws Exception {
        savePantryItemUsecase.init(NAME, PANTRY_ITEM_TYPE, QUANTITY, ADDING_DATE, BEST_BEFORE_DATE);
        execute();
    }

    private void execute() throws Exception {
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber<>();
        when(repository.addItem(getItem())).thenReturn(createSavedItemObservable());

        savePantryItemUsecase.execute().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();

        final List<PantryItem> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        PantryItem object = events.get(0);
        assertNotNull(object);
        assertEquals(ID, object.getId());
        assertPantryItemEquals(object);
    }

}