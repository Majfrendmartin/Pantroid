package com.wildeastcoders.pantroid.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface PantryItem {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    PantryItemType getType();

    void setType(PantryItemType type);

    Date getAddingDate();

    void setAddingDate(Date addingDate);

    Date getBestBeforeDate();

    void setBestBeforeDate(Date bestBeforeDate);

    int getQuantity();

    void setQuantity(int quantity);

    void update(@NonNull PantryItem otherItem);

}
