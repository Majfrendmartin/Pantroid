package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.view.EditTypeFragmentView;

/**
 * Created by Majfrendmartin on 2017-01-14.
 */
public class EditTypeFragmentPresenterImpl implements EditTypeFragmentPresenter {
    private final SavePantryItemTypeUsecase savePantryItemTypeUsecase;
    private final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;

    public EditTypeFragmentPresenterImpl(final SavePantryItemTypeUsecase savePantryItemTypeUsecase, final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase) {
        this.savePantryItemTypeUsecase = savePantryItemTypeUsecase;
        this.retrievePantryItemTypeUsecase = retrievePantryItemTypeUsecase;
    }

    @Override
    public void onSaveItemClicked(@NonNull final String name) {

    }

    @Override
    public void onSaveItemClicked(@NonNull final PantryItemType pantryItemType) {

    }

    @Override
    public void onCancelClicked() {

    }

    @Override
    public EditTypeFragmentView getView() {
        return null;
    }

    @Override
    public boolean isViewBounded() {
        return false;
    }

    @Override
    public void bindView(final EditTypeFragmentView view) {

    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSaveInstanceState(final Bundle bundle) {

    }
}
