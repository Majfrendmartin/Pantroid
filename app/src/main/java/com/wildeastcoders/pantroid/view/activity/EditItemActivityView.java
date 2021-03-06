package com.wildeastcoders.pantroid.view.activity;

import com.wildeastcoders.pantroid.view.View;

/**
 * Created by Majfrendmartin on 2016-11-08.
 */

public interface EditItemActivityView extends View {
    void displayBackConfirmation();

    void displayHomeConfirmation();

    void performBackNavigation();

    void performHomeNavigation();
}
