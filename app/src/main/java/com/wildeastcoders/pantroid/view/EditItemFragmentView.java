package com.wildeastcoders.pantroid.view;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.ValidationResult;

import java.util.Date;
import java.util.List;

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

    void displayNameValidationResult(ValidationResult validationResult);

    void displayQuantityValidationResult(ValidationResult validationResult);

    void displayTypeValidationResult(ValidationResult validationResult);

    void displayAddingDateValidationResult(ValidationResult validationResult);

    void displayBestBeforeDateValidationResult(ValidationResult validationResult);

    void displayErrorDialog();
}
