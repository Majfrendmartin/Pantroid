package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.database.Repository;

import java.util.List;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2017-01-04.
 */
public class RetrievePantryItemTypesUsecaseImpl implements RetrievePantryItemTypesUsecase {
    private final Repository repository;

    public RetrievePantryItemTypesUsecaseImpl(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<PantryItemType>> execute() {
        return repository.getTypes();
    }
}
