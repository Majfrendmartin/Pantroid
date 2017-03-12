package com.wildeastcoders.pantroid.injection.module;

import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.database.Repository;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.RemoveItemUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypesUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemsUsecase;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemsUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemUsecaseImpl;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecase;
import com.wildeastcoders.pantroid.model.usecase.UpdateItemQuantityUsecaseImpl;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenter;
import com.wildeastcoders.pantroid.presenter.EditItemActivityPresenterImpl;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.EditItemFragmentPresenterImpl;
import com.wildeastcoders.pantroid.presenter.MainActivityFragmentPresenter;
import com.wildeastcoders.pantroid.presenter.MainActivityFragmentPresenterImpl;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@Module
@PerActivity
public class PantryItemsModule {
    @Provides
    @PerActivity
    public MainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenterImpl();
    }

    @Provides
    @PerActivity
    public EditItemActivityPresenter provideEditItemActivityPresenter(EventBus eventBus) {
        return new EditItemActivityPresenterImpl(eventBus);
    }

    @Provides
    @PerActivity
    public MainActivityFragmentPresenter provideMainActivityFragmentPresenter(
            RetrievePantryItemsUsecase retrievePantryItemsUsecase,
            UpdateItemQuantityUsecase updateItemQuantityUsecase,
            RemoveItemUsecase removeItemUsecase) {
        return new MainActivityFragmentPresenterImpl(retrievePantryItemsUsecase,
                updateItemQuantityUsecase, removeItemUsecase);
    }

    @Provides
    @PerActivity
    public EditItemFragmentPresenter provideEditItemFragmentPresenter(
            FieldsValidator fieldsValidator,
            RetrievePantryItemUsecase retrievePantryItemUsecase,
            RetrievePantryItemTypesUsecase retrievePantryItemTypesUsecase,
            SavePantryItemUsecase savePantryItemUsecase,
            EventBus eventBus) {
        return new EditItemFragmentPresenterImpl(fieldsValidator, retrievePantryItemUsecase,
                retrievePantryItemTypesUsecase, savePantryItemUsecase, eventBus);
    }

    @Provides
    @PerActivity
    public UpdateItemQuantityUsecase provideUpdateItemQuantityUsecase(Repository repository) {
        return new UpdateItemQuantityUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public RetrievePantryItemsUsecase provideRetrievePantryItemsUsecase(Repository repository) {
        return new RetrievePantryItemsUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public RetrievePantryItemUsecase provideRetrievePantryItemUsecase(Repository repository) {
        return new RetrievePantryItemUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public RemoveItemUsecase provideRemoveItemUsecase(Repository repository) {
        return new RemoveItemUsecaseImpl(repository);
    }

    @Provides
    @PerActivity
    public SavePantryItemUsecase provideSavePantryItemUsecase(Repository repository) {
        return new SavePantryItemUsecaseImpl(repository);
    }
}
