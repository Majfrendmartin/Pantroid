package com.wildeastcoders.pantroid.injection.component;

import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.injection.module.ReadPantryItemTypesModule;
import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.view.EditItemActivity;
import com.wildeastcoders.pantroid.view.EditItemActivityFragment;

import dagger.Component;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class,
                PantryItemsModule.class,
                ReadPantryItemTypesModule.class})

public interface EditItemActivityComponent {
    void inject(EditItemActivity mainActivity);

    void inject(EditItemActivityFragment mainActivityFragment);
}
