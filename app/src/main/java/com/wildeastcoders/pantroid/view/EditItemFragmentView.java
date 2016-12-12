package com.wildeastcoders.pantroid.view;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItem.PantryItemFieldType;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.ValidationResult;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditItemFragmentView extends View {

    void populateTypesSpinner(List<PantryItemType> types);

    void setupNameField(String name);

    void setupQuantityField(int quantity);

    void setupAddingDateField(Date addingDate);

    void setupBestBeforeField(Date bestBeforeDate);

    void setupTypeField(PantryItemType type);

    void displayTypeErrorDialog();

    void displayDataErrorDialog();

    void displayPantryItemSavedDialog(PantryItem pantryItem);

    void displayNothingChangedDialog();

    void displayValidationResults(Map<PantryItemFieldType, ValidationResult> resultsMap);

    void finish();
}
