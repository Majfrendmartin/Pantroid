package com.wildeastcoders.pantroid.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerEditItemActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.injection.module.ReadPantryItemTypesModule;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditItemActivity extends PresenterActivity<EditItemActivityPresenter> implements EditItemActivityView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DaggerEditItemActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemsModule(new PantryItemsModule())
                .readPantryItemTypesModule(new ReadPantryItemTypesModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    @OnClick(R.id.fab)
    public void saveItemClicked() {
        presenter.onSaveItemClicked();
    }

    @Override
    public void handleSaveItemClicked() {
    }

    @Override
    public void displayBackConfirmation() {

    }

    @Override
    public void performBackNavigation() {

    }
}
