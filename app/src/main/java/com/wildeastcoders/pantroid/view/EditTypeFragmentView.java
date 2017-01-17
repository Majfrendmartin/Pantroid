package com.wildeastcoders.pantroid.view;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;


/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditTypeFragmentView extends View {
    void populateTypeDetails(@NonNull PantryItemType pantryItemType);
    void displayValidationError(@NonNull ValidationResult validationResult);
    void displayDiscardChangesDialogMessage();
    void displayTypeNotFoundErrorMessage();
    void finish();
}
