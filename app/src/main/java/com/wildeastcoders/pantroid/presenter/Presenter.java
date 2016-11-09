package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;

import com.wildeastcoders.pantroid.view.View;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface Presenter<T extends View> {
    void bindView(final T view);

    void unbindView();

    void onStart();

    void onStop();

    void onResume();

    void onPause();

    void onSaveInstanceState(final Bundle bundle);
}
