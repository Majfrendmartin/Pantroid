package com.wildeastcoders.pantroid.injection.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Majfrendmartin on 2017-02-05.
 */
@Scope
@Retention(RUNTIME)
public @interface PerApplication {
}
