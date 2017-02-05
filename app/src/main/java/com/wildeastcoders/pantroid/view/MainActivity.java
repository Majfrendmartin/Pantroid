package com.wildeastcoders.pantroid.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wildeastcoders.pantroid.PantroidApplication;
import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.ApplicationComponent;
import com.wildeastcoders.pantroid.injection.component.DaggerMainActivityComponent;
import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    MainActivityPresenter presenter;

    @Inject
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        final ApplicationComponent applicationComponent = ((PantroidApplication) getApplication()).getApplicationComponent();

        DaggerMainActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .pantryItemsModule(new PantryItemsModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    public void plusButtonClicked() {
        presenter.onAddNewItemSelected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_manage_types) {
            presenter.onManageTypesMenuOptionSelected();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startActivityForClass(Class<?> cls) {
        final Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void navigateToManageTypesActivity() {
        startActivityForClass(ManageTypesActivity.class);
    }

    @Override
    public void navigateToNewItemActivity() {
        startActivityForClass(EditItemActivity.class);
    }
}
