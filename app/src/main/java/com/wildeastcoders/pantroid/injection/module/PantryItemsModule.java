package com.wildeastcoders.pantroid.injection.module;

import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenter;
import com.wildeastcoders.pantroid.presenter.MainActivityPresenterImpl;

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
    public MainActivityPresenter provideMainActivityPresenter(){
        return new MainActivityPresenterImpl();
    }
}
