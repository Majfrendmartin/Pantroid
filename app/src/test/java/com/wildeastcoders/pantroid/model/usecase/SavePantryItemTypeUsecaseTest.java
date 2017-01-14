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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-11.
 */


@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class SavePantryItemTypeUsecaseTest {

    private static final String TYPE_NAME = "TYPE_NAME";
    private static final Long ID = 1L;
    private static final PantryItemType PANTRY_ITEM_TYPE = new PantryItemType(null, TYPE_NAME);
    private static final PantryItemType PANTRY_ITEM_TYPE_WITH_ID = new PantryItemType(ID, TYPE_NAME);
    @Mock
    private Repository repository;

    private SavePantryItemTypeUsecase savePantryItemTypeUsecase;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
        savePantryItemTypeUsecase = new SavePantryItemTypeUsecaseImpl(repository);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void initWithName() throws Exception {
        savePantryItemTypeUsecase.init(TYPE_NAME);
        final PantryItemType pantryItemType = getPantryItemTypeFromUsecase();
        assertNotNull(pantryItemType);
        assertEquals(TYPE_NAME, pantryItemType.getName());
        assertNull(pantryItemType.getId());
    }

    @Test
    public void initWithObject() throws Exception {
        savePantryItemTypeUsecase.init(PANTRY_ITEM_TYPE);
        final PantryItemType pantryItemType = getPantryItemTypeFromUsecase();
        assertEquals(PANTRY_ITEM_TYPE, pantryItemType);
    }

    @Test
    public void executeNotInitialized() throws Exception {
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        savePantryItemTypeUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertError(NullPointerException.class);
        final List<Throwable> errors = testSubscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertEquals(MISSING_PANTRY_ITEM_TYPE_OBJECT_ERROR_TEXT, errors.get(0).getMessage());
    }

    @Test
    public void executeInitializedByName() throws Exception {
        savePantryItemTypeUsecase.init(TYPE_NAME);
        execute(true);
    }

    @Test
    public void executeInitializedByObject() throws Exception {
        savePantryItemTypeUsecase.init(PANTRY_ITEM_TYPE);
        execute(true);
    }

    @Test
    public void executeInitializedByObjectWithID() throws Exception {
        savePantryItemTypeUsecase.init(PANTRY_ITEM_TYPE_WITH_ID);
        execute(false);
    }

    private PantryItemType getPantryItemTypeFromUsecase() {
        return ((SavePantryItemTypeUsecaseImpl) savePantryItemTypeUsecase).getPantryItemType();
    }

    private PantryItemType createSavedPantryItemType() {
        return new PantryItemType(ID, TYPE_NAME);
    }

    private void execute(boolean shouldAddNewType) throws Exception {
        final TestSubscriber<PantryItemType> testSubscriber = new TestSubscriber<>();
        final PantryItemType savedType = createSavedPantryItemType();
        final PantryItemType pantryItemTypeFromUsecase = getPantryItemTypeFromUsecase();

        when(repository.updateType(pantryItemTypeFromUsecase)).thenReturn(Observable.just(savedType));
        when(repository.addType(pantryItemTypeFromUsecase)).thenReturn(Observable.just(savedType));
        savePantryItemTypeUsecase.execute().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        final List<PantryItemType> events = testSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        final PantryItemType actual = events.get(0);
        assertEquals(savedType, actual);

        verify(repository, shouldAddNewType ? times(1) : never()).addType(pantryItemTypeFromUsecase);
        verify(repository, !shouldAddNewType ? times(1) : never()).updateType(pantryItemTypeFromUsecase);
    }
}