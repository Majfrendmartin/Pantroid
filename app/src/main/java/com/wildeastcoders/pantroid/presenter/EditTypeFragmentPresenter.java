package com.wildeastcoders.pantroid.presenter;

import android.support.annotation.NonNull;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.view.EditTypeFragmentView;

/**
 * Created by Majfrendmartin on 2016-11-09.
 */

public interface EditTypeFragmentPresenter extends Presenter<EditTypeFragmentView> {
    void onSaveItemClicked(@NonNull String name);

    void onSaveItemClicked(@NonNull PantryItemType pantryItemType);

    void onCancelClicked();
}
