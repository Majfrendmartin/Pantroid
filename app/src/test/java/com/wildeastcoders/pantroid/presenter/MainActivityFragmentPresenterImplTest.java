package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.DECREASE;
import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.INCREASE;
import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class MainActivityFragmentPresenterImplTest {

    public static final int CAPACITY = 10;

    @Mock
    private MainActivityFragmentView mainActivityFragmentView;

    @Mock
    private RetrievePantryItemsUsecase retrievePantryItemsUsecase;

    @Mock
    private UpdateItemQuantityUsecase updateItemQuantityUsecase;

    @Mock
    private RemoveItemUsecase removeItemUsecase;

    @Mock
    private PantryItem pantryItem;

    private MainActivityFragmentPresenterImpl presenter;

    private List<PantryItem> pantryItems;

    private static final Throwable THROWABLE = new Exception("EXCEPTION");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        pantryItems = new ArrayList<>(CAPACITY);

        for (int i = 0; i < CAPACITY; i++) {
            pantryItems.add(new PantryItemImpl());
        }

        presenter = new MainActivityFragmentPresenterImpl(retrievePantryItemsUsecase,
                updateItemQuantityUsecase, removeItemUsecase);

        when(retrievePantryItemsUsecase.execute()).thenReturn(Observable.just(pantryItems));
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.just(pantryItem));
        when(removeItemUsecase.execute()).thenReturn(Observable.just(pantryItem));

        setupRxAndroid();
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void requestPantryItemsUpdateViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        presenter.requestPantryItemsUpdate();
        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());

        presenter.requestPantryItemsUpdate();
        verify(retrievePantryItemsUsecase, times(2)).execute();
        verify(mainActivityFragmentView, times(2)).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());
    }

    @Test
    public void requestPantryItemsUpdateViewNotBounded() throws Exception {
        presenter.requestPantryItemsUpdate();
        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewBounded() throws Exception {
        when(retrievePantryItemsUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.bindView(mainActivityFragmentView);

        presenter.requestPantryItemsUpdate();
        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
        verify(mainActivityFragmentView).onDisplayGetItemsError(THROWABLE);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewNotBounded() throws Exception {
        when(retrievePantryItemsUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.requestPantryItemsUpdate();
        verify(retrievePantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
        verify(mainActivityFragmentView, never()).onDisplayGetItemsError(THROWABLE);
    }

    @Test
    public void onItemLongClickedViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        presenter.onItemLongClicked(pantryItem);
        verify(mainActivityFragmentView).onDisplayLongClickMenu(pantryItem);
    }

    @Test
    public void onItemLongClickedViewNotBounded() throws Exception {
        presenter.onItemLongClicked(pantryItem);
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

    private void selectMethodByOperation(QuantityUpdateOperation operation){
        switch (operation) {
            case INCREASE:
                presenter.onIncreaseItemsCountClicked(pantryItem);
                break;
            case DECREASE:
                presenter.onDecreaseItemsCountClicked(pantryItem);
                break;
        }
    }

    private void testContactItemCountChangeClickedViewBounded(QuantityUpdateOperation operation) {
        presenter.bindView(mainActivityFragmentView);
        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem).update(pantryItem);
        verify(mainActivityFragmentView).onUpdateItem(pantryItem);
    }

    private void testContactItemCountChangeClickedViewNotBounded(QuantityUpdateOperation operation) {
        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
    }

    private void testContactItemCountChangeClickedErrorViewBounded(QuantityUpdateOperation operation) {
        presenter.bindView(mainActivityFragmentView);
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem, never()).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
        verify(mainActivityFragmentView).onDisplayGetItemsError(THROWABLE);
    }

    private void testContactItemCountChangeClickedViewErrorNotBounded(QuantityUpdateOperation operation) {
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        selectMethodByOperation(operation);
        verify(updateItemQuantityUsecase).init(operation);
        verify(updateItemQuantityUsecase).execute();
        verify(pantryItem, never()).update(pantryItem);
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
        verify(mainActivityFragmentView, never()).onDisplayGetItemsError(THROWABLE);
    }

    @Test
    public void onRemoveItemClickedViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        presenter.onRemoveItemClicked(pantryItem);
        verify(removeItemUsecase).init(pantryItem);
        verify(removeItemUsecase).execute();
        verify(mainActivityFragmentView).onPantryItemRemoved(pantryItem);
    }

    @Test
    public void onRemoveItemClickedViewNotBounded() throws Exception {
        presenter.onRemoveItemClicked(pantryItem);
        verify(removeItemUsecase).init(pantryItem);
        verify(removeItemUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemRemoved(pantryItem);
    }

    @Test
    public void onRemoveItemClickedErrorViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        when(removeItemUsecase.execute()).thenReturn(Observable.error(THROWABLE));
    }

    @Test
    public void onRemoveItemClickedErrorViewNotBounded() throws Exception {
        when(removeItemUsecase.execute()).thenReturn(Observable.error(THROWABLE));
    }

    @Test
    public void onEditItemClickedViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        presenter.onEditItemClicked(pantryItem);
        verify(mainActivityFragmentView).onNavigateToEditItemActivity(pantryItem);
    }

    @Test
    public void onEditItemClickedViewNotBounded() throws Exception {
        presenter.onEditItemClicked(pantryItem);
        verify(mainActivityFragmentView, never()).onNavigateToEditItemActivity(pantryItem);
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