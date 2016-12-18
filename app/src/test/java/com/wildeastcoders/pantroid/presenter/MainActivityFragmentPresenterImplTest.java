package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.DECREASE;
import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.INCREASE;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.waitForAsyncOperationCompleted;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityFragmentPresenterImplTest {

    public static final int CAPACITY = 10;
    public static final Exception RETRIEVE_PANTRY_ITEMS_EXCEPTION = new Exception("RETRIEVE_PANTRY_ITEMS_EXCEPTION");
    public static final Exception UPDATE_ITEM_QUANTITY_EXCEPTION = new Exception("UPDATE_ITEM_QUANTITY_EXCEPTION");
    public static final Exception REMOVE_ITEM_EXCEPTION = new Exception("REMOVE_ITEM_EXCEPTION");

    @Mock
    private MainActivityFragmentView mainActivityFragmentView;

    @Mock
    private RetrievePantryItemsUsecase retrievePantryItemsUsecase;

    @Mock
    private UpdateItemQuantityUsecase updateItemQuantityUsecase;

    @Mock
    private RemoveItemUsecase removeItemUsecase;

    @Mock
    private RetrievePantryItemsUsecase retrievePantryItemsFailingUsecase;

    @Mock
    private UpdateItemQuantityUsecase updateItemQuantityFailingUsecase;

    @Mock
    private RemoveItemUsecase removeItemFailingUsecase;

    @Mock
    private PantryItem pantryItem;

    private MainActivityFragmentPresenterImpl presenter;

    private List<PantryItem> pantryItems;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pantryItems = new ArrayList<>(CAPACITY);

        for (int i = 0; i < CAPACITY; i++) {
            pantryItems.add(new PantryItem());
        }

        when(retrievePantryItemsUsecase.execute()).thenReturn(Observable.just(pantryItems));
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.just(pantryItem));
        when(removeItemUsecase.execute()).thenReturn(Observable.just(pantryItem));

        when(retrievePantryItemsFailingUsecase.execute())
                .thenReturn(Observable.error(RETRIEVE_PANTRY_ITEMS_EXCEPTION));
        when(updateItemQuantityFailingUsecase.execute())
                .thenReturn(Observable.error(UPDATE_ITEM_QUANTITY_EXCEPTION));
        when(removeItemFailingUsecase.execute())
                .thenReturn(Observable.error(REMOVE_ITEM_EXCEPTION));

        setupRxAndroid();
    }

    private void setupPresenter(RetrievePantryItemsUsecase retrievePantryItemsUsecase,
                                UpdateItemQuantityUsecase updateItemQuantityUsecase, RemoveItemUsecase removeItemUsecase) {
        presenter = new MainActivityFragmentPresenterImpl(retrievePantryItemsUsecase,
                updateItemQuantityUsecase, removeItemUsecase);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void requestPantryItemsUpdateViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);

        presenter.requestPantryItemsUpdate();
        waitForAsyncOperationCompleted();

        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());

        presenter.requestPantryItemsUpdate();
        waitForAsyncOperationCompleted();

        verify(retrievePantryItemsUsecase, times(2)).execute();
        verify(mainActivityFragmentView, times(2)).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());
    }

    @Test
    public void requestPantryItemsUpdateViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.requestPantryItemsUpdate();

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsFailingUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);
        presenter.requestPantryItemsUpdate();

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemsFailingUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
        verify(mainActivityFragmentView).onDisplayGetItemsError(RETRIEVE_PANTRY_ITEMS_EXCEPTION);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsFailingUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.requestPantryItemsUpdate();

        waitForAsyncOperationCompleted();

        verify(retrievePantryItemsFailingUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
        verify(mainActivityFragmentView, never()).onDisplayGetItemsError(RETRIEVE_PANTRY_ITEMS_EXCEPTION);
    }

    @Test
    public void onItemLongClickedViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);
        presenter.onItemLongClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(mainActivityFragmentView).onDisplayLongClickMenu(pantryItem);
    }

    @Test
    public void onItemLongClickedViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.onItemLongClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(mainActivityFragmentView, never()).onDisplayLongClickMenu(pantryItem);
    }

    @Test
    public void onIncreaseItemsCountClickedViewBounded() throws Exception {
        testContactItemCountChangeClickedViewBounded(INCREASE);
    }

    @Test
    public void onIncreaseItemsCountClickedViewNotBounded() throws Exception {
        testContactItemCountChangeClickedViewNotBounded(INCREASE);
    }

    @Test
    public void onDecreaseItemsCountClickedViewBounded() throws Exception {
        testContactItemCountChangeClickedViewBounded(DECREASE);
    }

    @Test
    public void onDecreaseItemsCountClickedViewNotBounded() throws Exception {
        testContactItemCountChangeClickedViewNotBounded(DECREASE);
    }

    @Test
    public void onIncreaseItemsCountClickedErrorViewBounded() throws Exception {
        testContactItemCountChangeClickedErrorViewBounded(INCREASE);
    }

    @Test
    public void onIncreaseItemsCountClickedErrorViewNotBounded() throws Exception {
        testContactItemCountChangeClickedViewErrorNotBounded(INCREASE);
    }

    @Test
    public void onDecreaseItemsCountClickedErrorViewBounded() throws Exception {
        testContactItemCountChangeClickedErrorViewBounded(DECREASE);
    }

    @Test
    public void onDecreaseItemsCountClickedErrorViewNotBounded() throws Exception {
        testContactItemCountChangeClickedViewErrorNotBounded(DECREASE);
    }

    private void selectMethodByOperation(QuantityUpdateOperation operation) throws InterruptedException {
        switch (operation) {
            case INCREASE:
                presenter.onIncreaseItemsCountClicked(pantryItem);
                break;
            case DECREASE:
                presenter.onDecreaseItemsCountClicked(pantryItem);
                break;
        }

        waitForAsyncOperationCompleted();
    }

    private void testContactItemCountChangeClickedViewBounded(QuantityUpdateOperation operation) throws InterruptedException {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);
        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem).update(pantryItem);
        verify(mainActivityFragmentView).onUpdateItem(pantryItem);
    }

    private void testContactItemCountChangeClickedViewNotBounded(QuantityUpdateOperation operation) throws InterruptedException {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
    }

    private void testContactItemCountChangeClickedErrorViewBounded(QuantityUpdateOperation operation) throws InterruptedException {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityFailingUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);
        selectMethodByOperation(operation);
        verify(updateItemQuantityFailingUsecase).init(operation);
        verify(updateItemQuantityFailingUsecase).execute();
        verify(pantryItem, never()).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
        verify(mainActivityFragmentView).onDisplayGetItemsError(UPDATE_ITEM_QUANTITY_EXCEPTION);
    }

    private void testContactItemCountChangeClickedViewErrorNotBounded(QuantityUpdateOperation operation) throws InterruptedException {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityFailingUsecase, removeItemUsecase);

        selectMethodByOperation(operation);
        verify(updateItemQuantityFailingUsecase).init(operation);
        verify(updateItemQuantityFailingUsecase).execute();
        verify(pantryItem, never()).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
        verify(mainActivityFragmentView, never()).onDisplayGetItemsError(UPDATE_ITEM_QUANTITY_EXCEPTION);
    }

    @Test
    public void onRemoveItemClickedViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.bindView(mainActivityFragmentView);
        presenter.onRemoveItemClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(removeItemUsecase).init(pantryItem);
        verify(removeItemUsecase).execute();
        verify(mainActivityFragmentView).onPantryItemRemoved(pantryItem);
    }

    @Test
    public void onRemoveItemClickedViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);

        presenter.onRemoveItemClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(removeItemUsecase).init(pantryItem);
        verify(removeItemUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemRemoved(pantryItem);
    }

    @Test
    public void onRemoveItemClickedErrorViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemFailingUsecase);
        presenter.bindView(mainActivityFragmentView);

        presenter.onRemoveItemClicked(pantryItem);
        waitForAsyncOperationCompleted();

        verify(removeItemFailingUsecase).init(pantryItem);
        verify(removeItemFailingUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemRemoved(pantryItem);
        verify(mainActivityFragmentView).onDisplayRemoveItemsError(REMOVE_ITEM_EXCEPTION);
    }

    @Test
    public void onRemoveItemClickedErrorViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemFailingUsecase);

        presenter.onRemoveItemClicked(pantryItem);
        waitForAsyncOperationCompleted();

        verify(removeItemFailingUsecase).init(pantryItem);
        verify(removeItemFailingUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemRemoved(pantryItem);
        verify(mainActivityFragmentView, never()).onDisplayRemoveItemsError(REMOVE_ITEM_EXCEPTION);
    }

    @Test
    public void onEditItemClickedViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);
        presenter.bindView(mainActivityFragmentView);
        presenter.onEditItemClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(mainActivityFragmentView).onNavigateToEditItemActivity(pantryItem);
    }

    @Test
    public void onEditItemClickedViewNotBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);
        presenter.onEditItemClicked(pantryItem);

        waitForAsyncOperationCompleted();

        verify(mainActivityFragmentView, never()).onNavigateToEditItemActivity(pantryItem);
    }

    @Test
    public void onCreateViewBounded() throws Exception {
        setupPresenter(retrievePantryItemsUsecase, updateItemQuantityUsecase, removeItemUsecase);
        presenter.bindView(mainActivityFragmentView);
        presenter.onCreate(null);

        waitForAsyncOperationCompleted();
        //TODO: is this test really needed?

    }

    @Test
    public void onStart() throws Exception {
        //TODO: implement me
    }

    @Test
    public void onStop() throws Exception {
        //TODO: implement me
    }

    @Test
    public void onResume() throws Exception {
        //TODO: implement me
    }

    @Test
    public void onPause() throws Exception {
        //TODO: implement me
    }

    @Test
    public void onSaveInstanceState() throws Exception {
        //TODO: implement me
    }

}