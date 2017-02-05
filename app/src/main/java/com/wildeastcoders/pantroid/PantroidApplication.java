package com.wildeastcoders.pantroid;

import android.app.Application;

import com.wildeastcoders.pantroid.injection.component.ApplicationComponent;
import com.wildeastcoders.pantroid.injection.component.DaggerApplicationComponent;
import com.wildeastcoders.pantroid.injection.module.ApplicationModule;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

public class PantroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupInjector();
    }

    private void setupInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
