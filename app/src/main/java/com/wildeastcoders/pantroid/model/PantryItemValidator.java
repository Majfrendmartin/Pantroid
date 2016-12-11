package com.wildeastcoders.pantroid.model;

import com.wildeastcoders.pantroid.model.database.ValidationResult;

import java.util.Date;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */

public interface PantryItemValidator {
    ValidationResult validateName(String name);
    ValidationResult validateType(PantryItemType type);
    ValidationResult validateQuantity(int quantity);
    ValidationResult validateAddingDate(Date addingDate);
    ValidationResult validateBestBeforeDate(Date addingDate);
}
