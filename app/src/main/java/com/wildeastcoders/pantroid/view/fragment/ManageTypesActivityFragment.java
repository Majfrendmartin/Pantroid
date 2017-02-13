package com.wildeastcoders.pantroid.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerManageTypesActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityFragmentPresenter;
import com.wildeastcoders.pantroid.view.adapter.PantryItemTypesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ManageTypesActivityFragment extends PresenterFragment<ManageTypesActivityFragmentPresenter> implements ManageTypesActivityFragmentView {

    @BindView(R.id.rv_types)
    RecyclerView rvTypes;

    @Inject
    Context context;

    private PantryItemTypesAdapter adapter;

    public ManageTypesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_manage_types, container, false);

        ButterKnife.bind(this, view);

        DaggerManageTypesActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemTypesModule(new PantryItemTypesModule())
                .build()
                .inject(this);
        presenter.bindView(this);

        setupRecyclerView();

        onCreateAfterInjection(savedInstanceState);

        return view;
    }

    private void setupRecyclerView() {
        rvTypes.setHasFixedSize(true);
        rvTypes.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PantryItemTypesAdapter();
        rvTypes.setAdapter(adapter);
    }

    @Override
    public void onItemsListChanged(final List<PantryItemType> itemTypes) {
        Toast.makeText(context, "Lista typów załadowana: " + itemTypes.size() , Toast.LENGTH_SHORT).show();
        adapter.updateItemsList(itemTypes);
    }

    @Override
    public void displayEditTypeDialog(final PantryItemType item) {

    }

    @Override
    public void displayCreateTypeDialog() {

    }

    @Override
    public void onUpdateItem(final int index, final PantryItemType item) {

    }

    @Override
    public void displayRetrieveTypesError() {

    }

    @Override
    public void displayItemOptions(final PantryItemType pantryItemType) {

    }

    @Override
    public void displayRemoveItemFailed() {

    }
}
