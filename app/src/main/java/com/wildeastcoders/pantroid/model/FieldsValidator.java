package com.wildeastcoders.pantroid.model;

import java.util.Date;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */

public interface FieldsValidator {
    ValidationResult validateName(String name);
    ValidationResult validateType(PantryItemType type);
    ValidationResult validateQuantity(int quantity);
    ValidationResult validateAddingDate(Date addingDate);
    ValidationResult validateBestBeforeDate(Date addingDate, Date bestBeforeDate);
}
