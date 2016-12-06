package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.view.View;

/**
 * Created by Majfrendmartin on 2016-11-07.
 */

public interface Presenter<T extends View> {

    T getView();

    boolean isViewBounded();

    void bindView(final T view);

    void unbindView();

    void onCreate(@Nullable Bundle bundle);

    void onDestroy();

    void onStart();

    void onStop();

    void onResume();

    void onPause();

    void onSaveInstanceState(final Bundle bundle);
}
