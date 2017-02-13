package com.wildeastcoders.pantroid.view.fragment;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.view.View;


/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditTypeFragmentView extends View {
    void populateTypeDetails(@NonNull PantryItemType pantryItemType);
    void displayValidationError(@NonNull ValidationResult validationResult);
    void displayDiscardChangesMessage();
    void displayTypeNotFoundErrorMessage();
    void displaySaveSucceedMessage();
    void displaySaveFailedMessage();
    void finish();
}
