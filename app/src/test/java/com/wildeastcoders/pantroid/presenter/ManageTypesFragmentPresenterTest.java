package com.wildeastcoders.pantroid.presenter;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RemoveTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;
import com.wildeastcoders.pantroid.view.ManageTypesFragmentView;

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

import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Majfrendmartin on 2017-01-31.
 */

@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class ManageTypesFragmentPresenterTest {

    private static final Throwable THROWABLE = new Throwable("Message");
    private static final long LIST_SIZE = 5;
    private static final List<PantryItemType> PANTRY_ITEM_TYPES = new ArrayList<PantryItemType>() {{
        for (long i = 1; i < LIST_SIZE; i++) {
            add(new PantryItemType(i, "PANTRY_ITEM_TYPE_" + i));
        }
    }};

    private static final PantryItemType PANTRY_ITEM_TYPE = PANTRY_ITEM_TYPES.get(0);

    @Mock
    private RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase;

    @Mock
    private RemoveTypeUsecase removeTypeUsecase;

    @Mock
    private ManageTypesFragmentView manageTypesFragmentView;

    private ManageTypesFragmentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();

        when(retrievePantryItemTypesUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPES));
        when(removeTypeUsecase.execute()).thenReturn(Observable.just(PANTRY_ITEM_TYPE));

        presenter = new ManageTypesFragmentPresenterImpl(retrievePantryItemTypesUsecase, removeTypeUsecase);
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void onCreateViewBounded() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        presenter.onCreate(null);

        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView).onItemsListChanged(PANTRY_ITEM_TYPES);
    }

    @Test
    public void onCreateViewBoundedRetrieveFailed() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        when(retrievePantryItemTypesUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.onCreate(null);

        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView, never()).onItemsListChanged(PANTRY_ITEM_TYPES);
        verify(manageTypesFragmentView).displayRetrieveTypesError();
    }

    @Test
    public void onCreateViewNotBounded() throws Exception {
        presenter.onCreate(null);

        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView, never()).onItemsListChanged(PANTRY_ITEM_TYPES);
    }

    @Test
    public void onCreateViewNotBoundedRetrieveFailed() throws Exception {
        presenter.onCreate(null);

        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView, never()).onItemsListChanged(PANTRY_ITEM_TYPES);
        verify(manageTypesFragmentView, never()).displayRetrieveTypesError();
    }

    @Test
    public void onItemLongClickedViewBounded() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        presenter.onItemLongClicked(PANTRY_ITEM_TYPE);

        verify(manageTypesFragmentView).displayItemOptions(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onItemLongClickedViewNotBounded() throws Exception {
        presenter.onItemLongClicked(PANTRY_ITEM_TYPE);

        verify(manageTypesFragmentView, never()).displayItemOptions(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onRemoveItemClickedViewBounded() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        presenter.onRemoveItemClicked(PANTRY_ITEM_TYPE);

        verify(removeTypeUsecase).init(PANTRY_ITEM_TYPE);
        verify(removeTypeUsecase).execute();
        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView).onItemsListChanged(PANTRY_ITEM_TYPES);
    }

    @Test
    public void onRemoveItemClickedViewNotBounded() throws Exception {
        presenter.onRemoveItemClicked(PANTRY_ITEM_TYPE);

        verify(removeTypeUsecase).init(PANTRY_ITEM_TYPE);
        verify(removeTypeUsecase).execute();
        verify(retrievePantryItemTypesUsecase).execute();
        verify(manageTypesFragmentView, never()).onItemsListChanged(PANTRY_ITEM_TYPES);
    }

    @Test
    public void onRemoveItemClickedViewBoundedFailed() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        when(removeTypeUsecase.execute()).thenReturn(Observable.error(THROWABLE));
        presenter.onRemoveItemClicked(PANTRY_ITEM_TYPE);

        verify(removeTypeUsecase).init(PANTRY_ITEM_TYPE);
        verify(removeTypeUsecase).execute();
        verify(retrievePantryItemTypesUsecase, never()).execute();
        verify(manageTypesFragmentView, never()).onItemsListChanged(PANTRY_ITEM_TYPES);
        verify(manageTypesFragmentView).displayRemoveItemFailed();
    }

    @Test
    public void onEditItemClickedViewBounded() throws Exception {
        presenter.bindView(manageTypesFragmentView);
        presenter.onEditItemClicked(PANTRY_ITEM_TYPE);
        verify(manageTypesFragmentView).displayEditTypeDialog(PANTRY_ITEM_TYPE);
    }

    @Test
    public void onEditItemClickedViewNotBounded() throws Exception {
        presenter.onEditItemClicked(PANTRY_ITEM_TYPE);
        verify(manageTypesFragmentView, never()).displayEditTypeDialog(PANTRY_ITEM_TYPE);
    }
}