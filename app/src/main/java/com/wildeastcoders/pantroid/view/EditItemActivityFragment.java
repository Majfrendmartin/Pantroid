package com.wildeastcoders.pantroid.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerEditItemActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditItemActivityFragment extends PresenterFragment<EditItemFragmentPresenter> implements EditItemActivityFragmentView {

    public EditItemActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerEditItemActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemsModule(new PantryItemsModule())
                .pantryItemTypesModule(new PantryItemTypesModule())
                .build()
                .inject(this);
        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void populateTypesSpinner(final List<PantryItemType> types) {

    }

    @Override
    public void setupNameField(final String name) {

    }

    @Override
    public void setupQuantityField(final int quantity) {

    }

    @Override
    public void setupAddingDateField(final Date addingDate) {

    }

    @Override
    public void setupBestBeforeField(final Date bestBeforeDate) {

    }

    @Override
    public void setupTypeField(final PantryItemType type) {

    }

    @Override
    public void displayTypeErrorDialog() {

    }

    @Override
    public void displayDataErrorDialog() {

    }

    @Override
    public void displayPantryItemSavedDialog(final PantryItem pantryItem) {

    }

    @Override
    public void displayNothingChangedDialog() {

    }

    @Override
    public void displayValidationResults(final Map<PantryItemFieldType, ValidationResult> resultsMap) {

    }

    @Override
    public void displayDiscardChangesDialog() {

    }

    @Override
    public void requestInputDataOnSaveItemClicked() {
        Toast.makeText(getContext(), "Dziala", Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {

    }
}
