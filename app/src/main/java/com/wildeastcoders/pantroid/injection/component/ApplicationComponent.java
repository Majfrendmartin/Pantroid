package com.wildeastcoders.pantroid.injection.component;

import com.wildeastcoders.pantroid.injection.module.ApplicationModule;
import com.wildeastcoders.pantroid.injection.module.RepositoryModule;
import com.wildeastcoders.pantroid.injection.scope.PerApplication;
import com.wildeastcoders.pantroid.model.database.Repository;

import dagger.Component;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@PerApplication
@Component(modules = {ApplicationModule.class,
        RepositoryModule.class})
public interface ApplicationComponent {
    Repository repository();
}
