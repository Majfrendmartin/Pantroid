package com.wildeastcoders.pantroid.injection.component;

import com.wildeastcoders.pantroid.injection.module.ActivityModule;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.injection.scope.PerActivity;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragment;
import com.wildeastcoders.pantroid.view.activity.ManageTypesActivity;
import com.wildeastcoders.pantroid.view.fragment.ManageTypesActivityFragment;

import dagger.Component;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class,
                PantryItemTypesModule.class})

public interface ManageTypesActivityComponent {
    void inject(ManageTypesActivity manageTypesActivity);

    void inject(ManageTypesActivityFragment manageTypesActivityFragment);

    void inject(EditTypeFragment manageTypesActivityFragment);
}
