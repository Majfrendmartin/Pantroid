package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.view.EditTypeFragmentView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.activities.IntentConstants.KEY_EDIT_ITEM_ID;

/**
 * Created by Majfrendmartin on 2017-01-14.
 */
public class EditTypeFragmentPresenterImpl extends AbstractPresenter<EditTypeFragmentView> implements EditTypeFragmentPresenter {
    private final SavePantryItemTypeUsecase savePantryItemTypeUsecase;
    private final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;

    private PantryItemType pantryItemType;

    public EditTypeFragmentPresenterImpl(final SavePantryItemTypeUsecase savePantryItemTypeUsecase, final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase) {
        this.savePantryItemTypeUsecase = savePantryItemTypeUsecase;
        this.retrievePantryItemTypeUsecase = retrievePantryItemTypeUsecase;
    }

    /**
     * For unit tests only!
     * @param pantryItemType
     */
    void setPantryItemType(final PantryItemType pantryItemType) {
        this.pantryItemType = pantryItemType;
    }

    @Override
    public void onSaveItemClicked(@NonNull final String name) {

    }

//    @Override
//    public void onSaveItemClicked(@NonNull final PantryItemType pantryItemType) {
//
//    }

    @Override
    public void onCancelClicked() {

    }

    @Override
    public void onFinishClicked() {

    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
        if (bundle != null &&  bundle.containsKey(KEY_EDIT_ITEM_ID)) {
            retrievePantryItemTypeUsecase.init(bundle.getLong(KEY_EDIT_ITEM_ID));
            retrievePantryItemTypeUsecase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(throwable -> null)
                    .subscribe(pantryItemType -> {
                        if (isViewBounded()) {
                            if (pantryItemType != null) {
                                getView().populateTypeDetails(pantryItemType);
                            } else {
                                getView().displayTypeNotFoundErrorMessage();
                            }
                        }
                    });
        }
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
