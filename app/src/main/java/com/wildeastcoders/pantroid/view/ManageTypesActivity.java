package com.wildeastcoders.pantroid.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerManageTypesActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageTypesActivity extends PresenterActivity<ManageTypesActivityPresenter> implements ManageTypesActivityView {

    public static final String EDIT_TYPE_FRAGMENT_TAG = "EDIT_TYPE_FRAGMENT_TAG";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_types);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DaggerManageTypesActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemTypesModule(new PantryItemTypesModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(final View view) {
        presenter.onAddButtonClicked();
    }

    @Override
    public void onNavigateBack() {

    }

    @Override
    public void showNewItemTypeDialog() {
        final FragmentManager supportFragmentManager = getSupportFragmentManager();
        final FragmentTransaction ft = supportFragmentManager.beginTransaction();
        final Fragment prev = supportFragmentManager.findFragmentByTag(EDIT_TYPE_FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        final EditTypeFragment editTypeFragment = new EditTypeFragment();
        editTypeFragment.show(supportFragmentManager, EDIT_TYPE_FRAGMENT_TAG);
    }
}
