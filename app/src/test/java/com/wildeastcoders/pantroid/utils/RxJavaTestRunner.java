package com.wildeastcoders.pantroid.utils;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import static com.wildeastcoders.pantroid.utils.TestUtils.setupTestRunner;

/**
 * Created by Majfrendmartin on 2016-11-27.
 */

public class RxJavaTestRunner extends BlockJUnit4ClassRunner {

    public RxJavaTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
        setupTestRunner();
    }
}
