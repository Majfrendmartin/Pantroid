package com.wildeastcoders.pantroid.model.usecase;

import rx.Observable;

/**
 * Created by Majfrendmartin on 2016-11-11.
 */

public interface Usecase<T> {
    Observable<T> execute();
}
