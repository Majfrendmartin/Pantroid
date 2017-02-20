package com.wildeastcoders.pantroid.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerEditItemActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.model.event.DialogButtonClickedEvent;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.view.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_BACK_DIALOG_ID;
import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_HOME_DIALOG_ID;
import static com.wildeastcoders.pantroid.view.ConfirmationDialogFragment.BUTTON_POSITIVE;

public class EditItemActivity extends PresenterActivity<EditItemActivityPresenter> implements EditItemActivityView {

    public static final String ABANDON_CHANGES_DIALOG_TAG = "ABANDON_CHANGES_DIALOG_TAG";

    @IdRes
    public static final int HOME_ID = android.R.id.home;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private PantryItemsModule pantryItemsModule;

    private Menu menu;

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
                .pantryItemsModule(getPantryItemsModule())
                .pantryItemTypesModule(new PantryItemTypesModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @NonNull
    private PantryItemsModule getPantryItemsModule() {
        if (pantryItemsModule == null) {
            pantryItemsModule = new PantryItemsModule();
        }
        return pantryItemsModule;
    }

    /**
     * For UNIT TESTs only. Setting custom module that injects mocks for certain classes.
     *
     * @param pantryItemModule Custom module.
     */
    void setPantryItemModule(@NonNull PantryItemsModule pantryItemModule) {
        this.pantryItemsModule = pantryItemModule;
    }

    @OnClick(R.id.fab)
    public void saveItemClicked() {
        presenter.onSaveItemClicked();
    }

    @Override
    public void displayBackConfirmation() {
        showConfirmationDialog(ABANDON_CHANGES_BACK_DIALOG_ID);
    }

    @Override
    public void displayHomeConfirmation() {
        showConfirmationDialog(ABANDON_CHANGES_HOME_DIALOG_ID);
    }

    private void showConfirmationDialog(int dialogId) {
        ViewUtils.showConfirmationAlertDialog(
                getSupportFragmentManager(),
                R.string.abandon_changes_dialog_title,
                R.string.abandon_changes_dialog_message,
                dialogId,
                ABANDON_CHANGES_DIALOG_TAG
        );
    }

    @Override
    public void performBackNavigation() {
        super.onBackPressed();
    }

    @Override
    public void performHomeNavigation() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @Subscribe
    public void onDialogButtonClickedEvent(@NonNull DialogButtonClickedEvent event) {
        if (event.getButtonId() == BUTTON_POSITIVE) {
            switch (event.getDialogId()) {
                case ABANDON_CHANGES_BACK_DIALOG_ID:
                    presenter.onBackConfirmed();
                    break;
                case ABANDON_CHANGES_HOME_DIALOG_ID:
                    presenter.onHomeConfirmed();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        presenter.onBackClicked();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == HOME_ID) {
            presenter.onHomeClicked();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
