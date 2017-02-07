package com.wildeastcoders.pantroid.injection.module;

import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecaseImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-07.
 */

@Module
@PerActivity
public class ReadPantryItemTypesModule {
    @Provides
    @PerActivity
    public RetrievePantryItemTypesUsecase provideRetrievePantryItemTypes(Repository repository) {
        return new RetrievePantryItemTypesUsecaseImpl(repository);
    }
    @Provides
    @PerActivity
    public RetrievePantryItemTypeUsecase provideRetrievePantryItemTypeUsecase(Repository repository) {
        return new RetrievePantryItemTypeUsecaseImpl(repository);
    }
}
