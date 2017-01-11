package com.wildeastcoders.pantroid.model;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */

@Entity
public class PantryItem {

    @Id
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Long typeId;

    @ToOne(joinProperty = "typeId")
    private PantryItemType type;

    @NonNull
    private Date addingDate;

    @NonNull
    private Date bestBeforeDate;

    @NonNull
    private int quantity;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1532991103)
    private transient PantryItemDao myDao;

    @Generated(hash = 506996655)
    private transient Long type__resolvedKey;

    public PantryItem(final String name, final int quantity, @NonNull final PantryItemType type, final Date addingDate, final Date bestBeforeDate) {
        id = null;
        this.name = name;
        // type field access needs to be done by getter/setter for greenDAO mechanism to work properly.
        setType(type);
        this.addingDate = addingDate;
        this.bestBeforeDate = bestBeforeDate;
        this.quantity = quantity;
    }

    @Generated(hash = 38461119)
    public PantryItem(Long id, @NonNull String name, @NonNull Long typeId, @NonNull Date addingDate, @NonNull Date bestBeforeDate, int quantity) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.addingDate = addingDate;
        this.bestBeforeDate = bestBeforeDate;
        this.quantity = quantity;
    }

    @Generated(hash = 478163975)
    public PantryItem() {
    }

    public PantryItem(final PantryItem pantryItem) {
        id = pantryItem.id;
        name = pantryItem.name;
        typeId = pantryItem.typeId;
        type = pantryItem.type;
        addingDate = pantryItem.addingDate;
        bestBeforeDate = pantryItem.bestBeforeDate;
        quantity = pantryItem.quantity;
    }

    public void update(@NonNull PantryItem otherItem) {
        // TODO: 11.11.2016 - implement this
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

    public Long getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Date getAddingDate() {
        return this.addingDate;
    }

    public void setAddingDate(Date addingDate) {
        this.addingDate = addingDate;
    }

    public Date getBestBeforeDate() {
        return this.bestBeforeDate;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 845983547)
    public PantryItemType getType() {
        Long __key = this.typeId;
        if (type__resolvedKey == null || !type__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PantryItemTypeDao targetDao = daoSession.getPantryItemTypeDao();
            PantryItemType typeNew = targetDao.load(__key);
            synchronized (this) {
                type = typeNew;
                type__resolvedKey = __key;
            }
        }
        return type;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 804455537)
    public void setType(@NonNull PantryItemType type) {
        if (type == null) {
            throw new DaoException("To-one property 'typeId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.type = type;
            typeId = type.getId();
            type__resolvedKey = typeId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    @Override
    public String toString() {
        String string = "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Quantity: " + quantity + "\n" +
                "Adding Date: " + addingDate + "\n" +
                "BB Date: " + bestBeforeDate + "\n" +
                "Type Id: " + typeId + "\n";
        try {
            string += "Type: [" + getType() + "]\n";
        } catch (DaoException e) {
            string += "Type: [NOT EVALUATED]\n";
        }
        return string;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PantryItem)) {
            return false;
        }

        final PantryItem o = (PantryItem) other;

        final boolean typeEquals = (getType() == null && o.getType() == null) ||
                (getType() != null && getType().equals(o.getType()));

        return typeEquals &&
                name.equals(o.name) &&
                id.equals(o.id) &&
                typeId.equals(o.typeId) &&
                addingDate.equals(o.addingDate) &&
                bestBeforeDate.equals(o.bestBeforeDate) &&
                quantity == o.quantity;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1652355626)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPantryItemDao() : null;
    }
}
