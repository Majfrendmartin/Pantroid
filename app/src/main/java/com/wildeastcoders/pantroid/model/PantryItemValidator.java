package com.wildeastcoders.pantroid.model;

import java.util.Date;

/**
 * Created by Majfrendmartin on 15.11.2016.
 */

public interface PantryItemValidator {
    boolean validateName(String name);
    boolean validateType(PantryItemType type);
    boolean validateQuantity(int quantity);
    boolean validateAddingDate(Date addingDate);
    boolean validateBestBeforeDate(Date addingDate);
}
