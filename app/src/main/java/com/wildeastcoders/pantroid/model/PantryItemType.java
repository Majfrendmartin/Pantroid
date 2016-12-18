package com.wildeastcoders.pantroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

@Entity
public class PantryItemType {
    @Id
    private Long id;

    @NotNull
    private String name;

    @Generated(hash = 701606119)
    public PantryItemType(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1391860181)
    public PantryItemType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Name: " + name;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PantryItemType)) {
            return false;
        }

        final PantryItemType o = (PantryItemType) other;
        return name.equals(o.name) && id.equals(o.id);
    }
}
