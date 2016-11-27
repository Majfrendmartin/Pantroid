package com.wildeastcoders.pantroid.utils;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by Majfrendmartin on 2016-11-27.
 */

public class RxJavaTestRunner extends RobolectricTestRunner {

    public RxJavaTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);

        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
    }
}
