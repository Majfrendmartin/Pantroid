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

import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.DECREASE;
import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.INCREASE;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-12.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class UpdateItemQuantityUsecaseTest {

    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(1L, "TYPE_NAME");
    public static final String NAME = "";
    public static final Long TYPE_ID = 1L;
    public static final Date ADDING_DATE = new Date();
    public static final Date BEST_BEFORE_DATE = new Date();
    public static final int QUANTITY = 1;
    public static final Long ID = 1L;
    private static final PantryItem PANTRY_ITEM = new PantryItem(ID, NAME, TYPE_ID, ADDING_DATE, BEST_BEFORE_DATE, QUANTITY);
    static {
        PANTRY_ITEM.setType(PANTRY_ITEM_TYPE);
    }

    @Mock
    private Repository repository;

    private UpdateItemQuantityUsecase updateItemQuantityUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        updateItemQuantityUsecase = new UpdateItemQuantityUsecaseImpl(repository);

        when(repository.updateItem(PANTRY_ITEM)).thenReturn(Observable.just(PANTRY_ITEM));
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void init() throws Exception {
        updateItemQuantityUsecase.init(PANTRY_ITEM, INCREASE);
        final UpdateItemQuantityUsecaseImpl usecaseImpl = (UpdateItemQuantityUsecaseImpl) updateItemQuantityUsecase;
        assertEquals(PANTRY_ITEM, usecaseImpl.getPantryItem());
        assertEquals(INCREASE, usecaseImpl.getQuantityUpdateOperation());

        updateItemQuantityUsecase.init(PANTRY_ITEM, DECREASE);

        assertEquals(PANTRY_ITEM, usecaseImpl.getPantryItem());
        assertEquals(DECREASE, usecaseImpl.getQuantityUpdateOperation());
    }

    @Test
    public void executeNotInitialized() throws Exception {
        final TestSubscriber<PantryItem> testSubscriber = new TestSubscriber();
        updateItemQuantityUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
    }

    @Test
    public void execute() throws Exception {
        updateItemQuantityUsecase.init(PANTRY_ITEM, INCREASE);
        final TestSubscriber testSubscriber = new TestSubscriber();
        updateItemQuantityUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItem> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        assertEquals(PANTRY_ITEM.getId(), events.get(0).getId());
    }


}