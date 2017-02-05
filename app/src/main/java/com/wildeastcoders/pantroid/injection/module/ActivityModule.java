package com.wildeastcoders.pantroid.injection.module;

import android.app.Activity;
import android.content.Context;

import com.wildeastcoders.pantroid.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(final Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Context provideContext() {
        return activity;
    }
}
