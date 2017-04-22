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

    void setupAddingDateField(@NonNull Date addingDate);

    void setupBestBeforeField(@NonNull Date bestBeforeDate);

    void setupTypeField(@NonNull PantryItemType type);

    void displayTypeErrorDialog();

    void displayDataErrorDialog();

    void displayPantryItemSavedDialog(@NonNull PantryItem pantryItem);

    void displayNothingChangedDialog();

    void displayValidationResults(@NonNull Map<PantryItemFieldType, ValidationResult> resultsMap);

    void displayDiscardChangesDialog();

    void requestInputDataOnSaveItemClicked();

    void finish();
}
