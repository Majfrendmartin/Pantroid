package com.wildeastcoders.pantroid.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */
public class PantryItemImpl implements PantryItem {

    private int id;
    private String name;
    private PantryItemType type;
    private Date addingDate;
    private Date bestBeforeDate;
    private int quantity;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public PantryItemType getType() {
        return type;
    }

    @Override
    public void setType(final PantryItemType type) {
        this.type = type;
    }

    @Override
    public Date getAddingDate() {
        return addingDate;
    }

    @Override
    public void setAddingDate(final Date addingDate) {
        this.addingDate = addingDate;
    }

    @Override
    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    @Override
    public void setBestBeforeDate(final Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void update(@NonNull PantryItem otherItem) {
        // TODO: 11.11.2016 - implement this
    }
}
