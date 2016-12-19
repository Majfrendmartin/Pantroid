package com.wildeastcoders.pantroid.model;

import android.text.TextUtils;

import com.wildeastcoders.pantroid.model.database.Repository;

import java.util.Date;

import static android.text.TextUtils.isEmpty;
import static com.wildeastcoders.pantroid.model.ValidationResult.*;

/**
 * Created by Majfrendmartin on 19.12.2016.
 */
public class PantryItemValidatorImpl implements PantryItemValidator {
    public static final int MAX_NAME_LENGTH = 40;
    private final Repository repository;

    public PantryItemValidatorImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public ValidationResult validateName(String name) {
        return getValidationResult(name != null && !isEmpty(name.trim()) && name.length() <= MAX_NAME_LENGTH);
    }

    @Override
    public ValidationResult validateType(PantryItemType type) {
        return null;
    }

    @Override
    public ValidationResult validateQuantity(int quantity) {
        return null;
    }

    @Override
    public ValidationResult validateAddingDate(Date addingDate) {
        return null;
    }

    @Override
    public ValidationResult validateBestBeforeDate(Date addingDate) {
        return null;
    }

    private ValidationResult getValidationResult(boolean isValid) {
        return isValid ? VALID : INVALID;
    }
}
