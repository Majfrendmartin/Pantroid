package com.wildeastcoders.pantroid.injection.module;

import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.model.usecase.RemoveTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.RemoveTypeUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecaseImpl;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenterImpl;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityFragmentPresenterImpl;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenter;
import com.wildeastcoders.pantroid.presenter.ManageTypesActivityPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-07.
 */

@Module
@PerActivity
public class PantryItemTypesModule {
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

    @Provides
    @PerActivity
    public RemoveTypeUsecase provideRemoveTypeUsecase(Repository repository) {
        return new RemoveTypeUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public SavePantryItemTypeUsecase provideSavePantryItemTypeUsecase(Repository repository) {
        return new SavePantryItemTypeUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public ManageTypesActivityPresenter provideManageTypesActivityPresenter() {
        return new ManageTypesActivityPresenterImpl();
    }

    @Provides
    @PerActivity
    public ManageTypesActivityFragmentPresenter provideManageTypesActivityFragmentPresenter(
            RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase, RemoveTypeUsecase removeTypeUsecase) {
        return new ManageTypesActivityFragmentPresenterImpl(retrievePantryItemTypesUsecase, removeTypeUsecase);
    }

    @Provides
    @PerActivity
    public EditTypeFragmentPresenter provideEditTypeFragmentPresenter(SavePantryItemTypeUsecase savePantryItemTypeUsecase,
                                                                      RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase,
                                                                      FieldsValidator fieldsValidator,
                                                                      EventBus eventBus) {
        return new EditTypeFragmentPresenterImpl(savePantryItemTypeUsecase, retrievePantryItemTypeUsecase,
                fieldsValidator, eventBus);
    }
}
