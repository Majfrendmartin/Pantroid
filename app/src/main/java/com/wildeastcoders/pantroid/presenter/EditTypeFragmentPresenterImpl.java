package com.wildeastcoders.pantroid.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wildeastcoders.pantroid.model.FieldsValidator;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.model.event.NewItemAddedEvent;
import com.wildeastcoders.pantroid.model.usecase.RetrievePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.model.usecase.SavePantryItemTypeUsecase;
import com.wildeastcoders.pantroid.view.fragment.EditTypeFragmentView;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.model.event.NewItemAddedEvent.EventItemTypes.ITEM_TYPE;
import static com.wildeastcoders.pantroid.view.IntentConstants.KEY_EDIT_ITEM_ID;
import static com.wildeastcoders.pantroid.model.ValidationResult.VALID;

/**
 * Created by Majfrendmartin on 2017-01-14.
 */
public class EditTypeFragmentPresenterImpl extends AbstractPresenter<EditTypeFragmentView> implements EditTypeFragmentPresenter {
    private final SavePantryItemTypeUsecase savePantryItemTypeUsecase;
    private final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase;
    private final FieldsValidator fieldsValidator;

    private PantryItemType pantryItemType;

    public EditTypeFragmentPresenterImpl(final SavePantryItemTypeUsecase savePantryItemTypeUsecase, final RetrievePantryItemTypeUsecase retrievePantryItemTypeUsecase, final FieldsValidator fieldsValidator) {
        this.savePantryItemTypeUsecase = savePantryItemTypeUsecase;
        this.retrievePantryItemTypeUsecase = retrievePantryItemTypeUsecase;
        this.fieldsValidator = fieldsValidator;
    }

    /**
     * For unit tests only!
     *
     * @param pantryItemType
     */
    void setPantryItemType(final PantryItemType pantryItemType) {
        this.pantryItemType = pantryItemType;
    }

    @Override
    public void onSaveItemClicked(@NonNull final String name) {

        final ValidationResult validationResult = fieldsValidator.validateName(name);
        if (validationResult != VALID) {
            if (isViewBounded()) {
                getView().displayValidationError(validationResult);
            }
            return;
        }

        if (pantryItemType != null) {
            pantryItemType.setName(name);
            savePantryItemTypeUsecase.init(pantryItemType);
        } else {
            savePantryItemTypeUsecase.init(name);
        }

        savePantryItemTypeUsecase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> null)
                .subscribe(item -> {
                    handleSaveResult(item);
                });
    }

    private void handleSaveResult(@Nullable final PantryItemType item) {
        if (isViewBounded()) {
            if (item != null) {
                EventBus.getDefault().post(new NewItemAddedEvent(ITEM_TYPE));
                getView().displaySaveSucceedMessage();
                getView().finish();
            } else {
                getView().displaySaveFailedMessage();
            }
        }
    }

    @Override
    public void onCancelClicked() {
        if (isViewBounded()) {
            getView().displayDiscardChangesMessage();
        }
    }

    @Override
    public void onFinishClicked() {
        if (isViewBounded()) {
            getView().finish();
        }
    }

    @Override
    public void onCreate(@Nullable final Bundle bundle) {
        if (bundle != null && bundle.containsKey(KEY_EDIT_ITEM_ID)) {
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
