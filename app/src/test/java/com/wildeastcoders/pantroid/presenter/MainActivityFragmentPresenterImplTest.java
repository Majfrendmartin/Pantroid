package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemImpl;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemsUsecase;
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
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.DECREASE;
import static com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase.QuantityUpdateOperation.INCREASE;
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
    private GetPantryItemsUsecase getPantryItemsUsecase;

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

        presenter = new MainActivityFragmentPresenterImpl(getPantryItemsUsecase,
                updateItemQuantityUsecase, removeItemUsecase);

        when(getPantryItemsUsecase.execute()).thenReturn(Observable.just(pantryItems));
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.just(pantryItem));
        when(removeItemUsecase.execute()).thenReturn(Observable.just(pantryItem));

        setupRxAndroid();
    }

    private void setupRxAndroid() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void requestPantryItemsUpdateViewBounded() throws Exception {
        presenter.bindView(mainActivityFragmentView);
        presenter.requestPantryItemsUpdate();
        verify(getPantryItemsUsecase).execute();
        verify(mainActivityFragmentView).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());

        presenter.requestPantryItemsUpdate();
        verify(getPantryItemsUsecase, times(2)).execute();
        verify(mainActivityFragmentView, times(2)).onPantryItemsListChanged(pantryItems);
        assertEquals(pantryItems, presenter.getPantryItems());
    }

    @Test
    public void requestPantryItemsUpdateViewNotBounded() throws Exception {
        presenter.requestPantryItemsUpdate();
        verify(getPantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewBounded() throws Exception {
        when(getPantryItemsUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.bindView(mainActivityFragmentView);

        presenter.requestPantryItemsUpdate();
        verify(getPantryItemsUsecase).execute();
        verify(mainActivityFragmentView, never()).onPantryItemsListChanged(pantryItems);
        verify(mainActivityFragmentView).onDisplayGetItemsError(THROWABLE);
    }

    @Test
    public void requestPantryItemsUpdateErrorViewNotBounded() throws Exception {
        when(getPantryItemsUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.requestPantryItemsUpdate();
        verify(getPantryItemsUsecase).execute();
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
    public void onEditItemClicked() throws Exception {

    }

    @Test
    public void onStart() throws Exception {

    }

    @Test
    public void onStop() throws Exception {

    }

    @Test
    public void onResume() throws Exception {

    }

    @Test
    public void onPause() throws Exception {

    }

    @Test
    public void onSaveInstanceState() throws Exception {

    }

}