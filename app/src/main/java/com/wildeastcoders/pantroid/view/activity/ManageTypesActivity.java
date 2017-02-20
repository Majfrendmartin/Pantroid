package com.wildeastcoders.pantroid.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerManageTypesActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;
import com.wildeastcoders.pantroid.view.ViewUtils;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragment;

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
        final EditTypeFragment editTypeFragment = new EditTypeFragment();
        ViewUtils.showDialogFragment(editTypeFragment, getSupportFragmentManager(), EDIT_TYPE_FRAGMENT_TAG);
    }
}
