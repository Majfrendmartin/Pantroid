package com.wildeastcoders.pantroid.injection.component;

import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.view.MainActivity;
import com.wildeastcoders.pantroid.view.MainActivityFragment;

import dagger.Component;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class,
                PantryItemsModule.class})

public interface MainActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(MainActivityFragment mainActivityFragment);
}
