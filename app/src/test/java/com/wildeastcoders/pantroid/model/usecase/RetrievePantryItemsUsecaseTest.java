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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-02.
 */


@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class RetrievePantryItemsUsecaseTest {

    private static final Throwable THROWABLE = new Throwable("Message");
    private static final long LIST_SIZE = 5;
    private static final List<PantryItem> ITEMS_LIST = new ArrayList<PantryItem>() {{
        for (long i = 1; i < LIST_SIZE; i++) {
            add(new PantryItem(i, "PANTRY_ITEM_" + i, 1L, new Date(), new Date(), 1));
        }
    }};

    private RetrievePantryItemsUsecase retrievePantryItemsUsecase;

    @Mock
    private Repository repository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        retrievePantryItemsUsecase = new RetrievePantryItemsUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void execute() throws Exception {
        when(repository.getItems()).thenReturn(Observable.just(ITEMS_LIST));

        final TestSubscriber<List<PantryItem>> testSubscriber = new TestSubscriber<>();

        retrievePantryItemsUsecase.execute()
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<List<PantryItem>> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        assertEquals(ITEMS_LIST, events.get(0));
    }


    @Test
    public void executeWithException() throws Exception {
        when(repository.getItems()).thenReturn(Observable.error(THROWABLE));
        final TestSubscriber<List<PantryItem>> testSubscriber = new TestSubscriber<>();

        retrievePantryItemsUsecase.execute()
                .subscribe(testSubscriber);

        testSubscriber.assertError(Throwable.class);
        final List<Throwable> throwables = testSubscriber.getOnErrorEvents();
        assertEquals(1, throwables.size());
        assertEquals(THROWABLE, throwables.get(0));
    }

}