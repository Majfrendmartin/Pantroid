package com.wildeastcoders.pantroid.model.usecase;

import android.support.annotation.NonNull;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */

public interface Usecase<T> {
    @NonNull
    Observable<T> execute();
}
