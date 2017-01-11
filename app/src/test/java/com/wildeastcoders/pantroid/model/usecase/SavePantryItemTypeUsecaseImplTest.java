package com.wildeastcoders.pantroid.model.usecase;

import com.wildeastcoders.pantroid.BuildConfig;
import com.wildeastcoders.pantroid.utils.RxJavaTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static com.wildeastcoders.pantroid.utils.TestUtils.setupRxAndroid;
import static com.wildeastcoders.pantroid.utils.TestUtils.tearDownRxAndroid;

/**
 * Created by Majfrendmartin on 2017-01-11.
 */


@RunWith(RxJavaTestRunner.class)
@Config(constants = BuildConfig.class)
public class SavePantryItemTypeUsecaseImplTest {
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setupRxAndroid();
    }

    @After
    public void tearDown() throws Exception {
        tearDownRxAndroid();
    }

    @Test
    public void initWithName() throws Exception {

    }

    @Test
    public void initWithObject() throws Exception {

    }

    @Test
    public void executeNotInitialized() throws Exception {

    }

    @Test
    public void executeWithName() throws Exception {

    }

    @Test
    public void executeWithObject() throws Exception {

    }
}