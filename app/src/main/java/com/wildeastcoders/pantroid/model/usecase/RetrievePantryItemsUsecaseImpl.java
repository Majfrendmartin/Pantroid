package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItem;
import com.wildeastcoders.pantroid.model.database.Repository;

import java.util.List;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-02.
 */
public class RetrievePantryItemsUsecaseImpl implements RetrievePantryItemsUsecase {
    private final Repository repository;

    public RetrievePantryItemsUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<PantryItem>> execute() {
        return repository.getItems();
    }
}
