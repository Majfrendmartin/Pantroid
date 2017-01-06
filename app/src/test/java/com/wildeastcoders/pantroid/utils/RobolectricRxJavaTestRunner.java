package com.wildeastcoders.pantroid.utils;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

import static com.wildeastcoders.pantroid.utils.TestUtils.setupTestRunner;

/**
 * Created by Majfrendmartin on 2017-01-06.
 */

public class RobolectricRxJavaTestRunner extends RobolectricTestRunner {

    public RobolectricRxJavaTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
        setupTestRunner();
    }
}
