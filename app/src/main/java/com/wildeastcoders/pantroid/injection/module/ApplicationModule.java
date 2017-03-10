package com.wildeastcoders.pantroid.injection.module;

import android.app.Application;

import com.wildeastcoders.pantroid.PantroidApplication;
import com.wildeastcoders.pantroid.injection.scope.PerApplication;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@Module
public class ApplicationModule {
    private final PantroidApplication application;

    public ApplicationModule(final PantroidApplication application) {
        this.application = application;
    }

    @Provides
    @PerApplication
    public PantroidApplication providePantroidApplication() {
        return application;
    }

    @Provides
    @PerApplication
    public Application provideApplication() {
        return application;
    }

    @Provides
    @PerApplication
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
