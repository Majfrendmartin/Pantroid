package com.wildeastcoders.pantroid.injection.component;

import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemsModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.view.activity.EditItemActivity;
import com.wildeastcoders.pantroid.view.fragment.EditItemActivityFragment;

import dagger.Component;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class,
                PantryItemsModule.class,
                PantryItemTypesModule.class})

public interface EditItemActivityComponent {
    void inject(EditItemActivity mainActivity);

    void inject(EditItemActivityFragment mainActivityFragment);
}
