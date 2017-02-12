package com.wildeastcoders.pantroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wildeastcoders.pantroid.PantroidApplication;
import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.ApplicationComponent;
import com.wildeastcoders.pantroid.injection.component.DaggerMainActivityComponent;
import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.presenter.MainActivityFragmentPresenter;
import com.wildeastcoders.pantroid.view.MainActivityFragmentView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends PresenterFragment<MainActivityFragmentPresenter> implements MainActivityFragmentView {

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FragmentActivity activity = getActivity();
        final ApplicationComponent applicationComponent = ((PantroidApplication) activity.getApplication()).getApplicationComponent();

        DaggerMainActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(activity))
                .pantryItemsModule(new PantryItemsModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPantryItemsListChanged(final List<PantryItem> pantryItemList) {
        Snackbar.make(getView(), "List changed", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onDisplayLongClickMenu(@NonNull final PantryItem item) {

    }

    @Override
    public void onNavigateToEditItemActivity(@NonNull final PantryItem item) {

    }

    @Override
    public void onUpdateItem(@NonNull final PantryItem item) {

    }

    @Override
    public void onDisplayGetItemsError(final Throwable throwable) {

    }

    @Override
    public void onDisplayRemoveItemsError(final Throwable throwable) {

    }

    @Override
    public void onPantryItemRemoved(final PantryItem item) {

    }
}
