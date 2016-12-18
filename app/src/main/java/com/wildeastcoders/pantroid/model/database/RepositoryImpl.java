package com.wildeastcoders.pantroid.model.database;

import com.wildeastcoders.pantroid.model.DaoSession;
import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.PantryItemType;

import java.util.List;

import rx.Observable;


/**
 * Created by Majfrendmartin on 2016-12-14.
 */

public class RepositoryImpl implements Repository {

    private final DaoSession daoSession;

    public RepositoryImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Override
    public Observable<List<PantryItem>> getItems() {
        return daoSession.getPantryItemDao().rx().loadAll();
    }

    @Override
    public Observable<List<PantryItemType>> getTypes() {
        return daoSession.getPantryItemTypeDao().rx().loadAll();
    }

    @Override
    public Observable<PantryItem> addItem(final PantryItem item) {
        return daoSession.getPantryItemDao().rx().insert(item);
    }

    @Override
    public Observable<PantryItemType> addType(final PantryItemType item) {
        return null;
    }

    @Override
    public Observable<Boolean> updateItem(final PantryItem item) {
        return null;
    }

    @Override
    public Observable<Boolean> updateType(final PantryItemType item) {
        return null;
    }

    @Override
    public Observable<Boolean> removeItem(final PantryItem item) {
        return null;
    }

    @Override
    public Observable<Boolean> removeType(final PantryItemType item) {
        return null;
    }
}
