package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemImpl;
import com.wildeastcoders.pantroid.model.usecase.GetPantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
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

import static org.mockito.Mockito.never;
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

        presenter = new MainActivityFragmentPresenterImpl(getPantryItemsUsecase, updateItemQuantityUsecase);

        when(getPantryItemsUsecase.execute()).thenReturn(Observable.just(pantryItems));
        when(updateItemQuantityUsecase.execute()).thenReturn(Observable.just(pantryItem));

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
        presenter.bindView(mainActivityFragmentView);
        presenter.onIncreaseItemsCountClicked(pantryItem);
        verify(updateItemQuantityUsecase).execute();
        verify(mainActivityFragmentView).onUpdateItem(pantryItem);
    }

    @Test
    public void onIncreaseItemsCountClickedViewNotBounded() throws Exception {
        presenter.onIncreaseItemsCountClicked(pantryItem);
        verify(updateItemQuantityUsecase).execute();
        verify(mainActivityFragmentView, never()).onUpdateItem(pantryItem);
    }

    //TODO: add error handling tests.

    @Test
    public void onDecreaseItemsCountClicked() throws Exception {

    }

    @Test
    public void onRemoveItemClicked() throws Exception {

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