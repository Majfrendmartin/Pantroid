package com.wildeastcoders.pantroid.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerManageTypesActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityFragmentPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ManageTypesActivityFragment extends PresenterFragment<ManageTypesActivityFragmentPresenter> implements ManageTypesActivityFragmentView {

    @Inject
    Context context;

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
        onCreateAfterInjection(savedInstanceState);

        return view;
    }

    @Override
    public void onItemsListChanged(final List<PantryItemType> itemTypes) {
        Toast.makeText(context, "Lista typów załadowana: " + itemTypes.size() , Toast.LENGTH_SHORT).show();
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
