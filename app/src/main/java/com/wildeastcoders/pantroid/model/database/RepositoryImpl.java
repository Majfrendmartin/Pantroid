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
        return daoSession.getPantryItemTypeDao().rx().insert(item);
    }

    @Override
    public Observable<PantryItem> updateItem(final PantryItem item) {
        return daoSession.getPantryItemDao().rx().update(item);
    }

    @Override
    public Observable<PantryItemType> updateType(final PantryItemType item) {
        return daoSession.getPantryItemTypeDao().rx().update(item);
    }

    @Override
    public Observable<Void> removeItem(final PantryItem item) {
        return daoSession.getPantryItemDao().rx().delete(item);
    }

    @Override
    public Observable<Void> removeType(final PantryItemType item) {
        return daoSession.getPantryItemTypeDao().rx().delete(item);
    }

    @Override
    public Observable<PantryItem> getItemById(final Long id) {
        return daoSession.getPantryItemDao().rx().load(id);
    }

    @Override
    public Observable<PantryItemType> getTypeById(Long id) {
        return daoSession.getPantryItemTypeDao().rx().load(id);
    }
}
