package com.wildeastcoders.pantroid.view.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.view.View;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditItemActivityFragmentView extends View {

    void populateTypesSpinner(@NonNull List<PantryItemType> types);

    void setupNameField(@Nullable String name);

    void setupQuantityField(int quantity);

    void setupAddingDateField(Date addingDate);

    void setupBestBeforeField(Date bestBeforeDate);

    void setupTypeField(PantryItemType type);

    void displayTypeErrorDialog();

    void displayDataErrorDialog();

    void displayPantryItemSavedDialog(PantryItem pantryItem);

    void displayNothingChangedDialog();

    void displayValidationResults(Map<PantryItemFieldType, ValidationResult> resultsMap);

    void displayDiscardChangesDialog();

    void requestInputDataOnSaveItemClicked();

    void finish();
}
