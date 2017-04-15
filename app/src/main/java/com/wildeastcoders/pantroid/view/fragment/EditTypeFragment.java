package com.wildeastcoders.pantroid.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.injection.component.DaggerManageTypesActivityComponent;
import com.wildeastcoders.pantroid.injection.module.PantryItemTypesModule;
import com.wildeastcoders.pantroid.model.PantryItemType;
import com.wildeastcoders.pantroid.model.ValidationResult;
import com.wildeastcoders.pantroid.presenter.EditTypeFragmentPresenter;
import com.wildeastcoders.pantroid.view.IntentConstants;
import com.wildeastcoders.pantroid.view.ViewUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wildeastcoders.pantroid.model.Constants.DialogIdentifiers.ABANDON_CHANGES_BACK_DIALOG_ID;
import static com.wildeastcoders.pantroid.view.activity.EditItemActivity.ABANDON_CHANGES_DIALOG_TAG;


/**
 * Created by Majfrendmartin on 2017-02-11.
 */

public class EditTypeFragment extends PresenterDialogFragment<EditTypeFragmentPresenter>
        implements EditTypeFragmentView {

    @BindView(R.id.et_type_name)
    EditText etTypeName;

    @Inject
    Context context;

    private PantryItemTypesModule pantryItemTypesModule;

    public static EditTypeFragment newInstance() {
        return new EditTypeFragment();
    }

    public static EditTypeFragment newInstance(long typeId) {
        final EditTypeFragment editTypeFragment = new EditTypeFragment();

        final Bundle bundle = new Bundle(1);
        bundle.putLong(IntentConstants.KEY_EDIT_TYPE_ID, typeId);
        editTypeFragment.setArguments(bundle);

        return editTypeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerManageTypesActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .pantryItemTypesModule(getPantryItemTypesModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
    }

    void setPantryItemTypesModule(@NonNull PantryItemTypesModule pantryItemTypesModule) {
        this.pantryItemTypesModule = pantryItemTypesModule;
    }

    @NonNull
    private PantryItemTypesModule getPantryItemTypesModule() {
        if (pantryItemTypesModule == null) {
            pantryItemTypesModule = new PantryItemTypesModule();
        }
        return pantryItemTypesModule;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_type, container, false);

        getDialog().setTitle(R.string.edit_type_dialog_title);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_save)
    public void onSaveClicked() {
        presenter.onSaveItemClicked(etTypeName.getText().toString());
    }

    @Override
    public void populateTypeDetails(@NonNull final PantryItemType pantryItemType) {
        etTypeName.setText(pantryItemType.getName());
    }

    @Override
    public void displayValidationError(@NonNull final ValidationResult validationResult) {
        Toast.makeText(context, R.string.edit_type_validation_error_text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayDiscardChangesMessage() {
        showConfirmationDialog(ABANDON_CHANGES_BACK_DIALOG_ID);
    }

    @Override
    public void displayTypeNotFoundErrorMessage() {
        Toast.makeText(context, R.string.edit_type_type_not_found_text, Toast.LENGTH_LONG).show();
    }

    private void showConfirmationDialog(int dialogId) {
        ViewUtils.showConfirmationAlertDialog(
                getActivity().getSupportFragmentManager(),
                R.string.abandon_changes_dialog_title,
                R.string.abandon_changes_dialog_message,
                dialogId,
                ABANDON_CHANGES_DIALOG_TAG
        );
    }

    @Override
    public void displaySaveSucceedMessage() {
        Toast.makeText(context, R.string.edit_type_item_saved_successfully, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displaySaveFailedMessage() {
        Toast.makeText(context, R.string.edit_type_item_saving_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
        dismiss();
    }
}
