package com.wildeastcoders.pantroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.wildeastcoders.pantroid.view.DatePickerButton;
import com.wildeastcoders.pantroid.view.IntentConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditItemActivityFragment extends PresenterFragment<EditItemFragmentPresenter> implements EditItemActivityFragmentView {

    public static final SimpleDateFormat DATE_FORMAT_FOR_BUTTONS = new SimpleDateFormat("yyyy MMM dd");

    private PantryItemTypesModule pantryItemTypesModule;
    private PantryItemsModule pantryItemsModule;

    @BindView(R.id.sp_item_type)
    Spinner spItemType;

    @BindView(R.id.et_item_name)
    EditText etItemName;

    @BindView(R.id.et_item_quantity)
    EditText etItemQuantity;

    @BindView(R.id.btn_item_adding_date)
    DatePickerButton btnItemAddingDate;

    public static EditItemActivityFragment newInstance() {
        return new EditItemActivityFragment();
    }

    public static EditItemActivityFragment newInstance(long pantryItemId) {
        final EditItemActivityFragment fragment = new EditItemActivityFragment();
        final Bundle bundle = new Bundle(1);
        bundle.putLong(IntentConstants.KEY_EDIT_ITEM_ID, pantryItemId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerEditItemActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemsModule(getPantryItemsModule())
                .pantryItemTypesModule(getPantryItemTypesModule())
                .build()
                .inject(this);
        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    void setPantryItemTypesModule(@NonNull PantryItemTypesModule pantryItemTypesModule) {
        this.pantryItemTypesModule = pantryItemTypesModule;
    }

    @NonNull
    private PantryItemTypesModule getPantryItemTypesModule() {
        if (pantryItemTypesModule == null) {
            pantryItemTypesModule = new PantryItemTypesModule();
        }
        return pantryItemTypesModule;
    }

    void setPantryItemsModule(@NonNull PantryItemsModule pantryItemsModule){
        this.pantryItemsModule = pantryItemsModule;
    }

    @NonNull
    private PantryItemsModule getPantryItemsModule() {
        if (pantryItemsModule == null) {
            pantryItemsModule = new PantryItemsModule();
        }
        return pantryItemsModule;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void populateTypesSpinner(@NonNull final List<PantryItemType> types) {
        final ArrayAdapter<PantryItemType> pantryItemTypeArrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, types);
        pantryItemTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spItemType.setAdapter(pantryItemTypeArrayAdapter);
    }

    @Override
    public void setupNameField(final String name) {
        etItemName.setText(name);
    }

    @Override
    public void setupQuantityField(final int quantity) {
        this.etItemQuantity.setText(Integer.toString(quantity));
    }

    @Override
    public void setupAddingDateField(final Date addingDate) {
        btnItemAddingDate.setDate(addingDate);
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
