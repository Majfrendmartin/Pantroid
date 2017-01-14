package com.wildeastcoders.pantroid.model;

import java.util.Date;

import static android.text.TextUtils.isEmpty;
import static com.wildeastcoders.pantroid.model.ValidationResult.*;

/**
 * Created by Majfrendmartin on 19.12.2016.
 */
public class PantryItemValidatorImpl implements PantryItemValidator {

    @Override
    public ValidationResult validateName(String name) {
        return getValidationResult((name != null) && !isEmpty(name.trim()) && (name.length() <= Constants.MAX_NAME_LENGTH));
    }

    @Override
    public ValidationResult validateType(PantryItemType type) {
        return getValidationResult((type != null) && (type.getId() > 0) && !isEmpty(type.getName().trim()));
    }

    @Override
    public ValidationResult validateQuantity(int quantity) {
        return getValidationResult(quantity > 0);
    }

    @Override
    public ValidationResult validateAddingDate(Date addingDate) {
        return getValidationResult(addingDate != null);
    }

    @Override
    public ValidationResult validateBestBeforeDate(Date addingDate, Date bestBeforeDate) {
        return addingDate == null || bestBeforeDate == null ? INVALID :
                getValidationResult(bestBeforeDate.after(addingDate));
    }

    private ValidationResult getValidationResult(boolean isValid) {
        return isValid ? VALID : INVALID;
    }
}
