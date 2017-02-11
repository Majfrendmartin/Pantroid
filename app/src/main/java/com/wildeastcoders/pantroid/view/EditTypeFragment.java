package com.wildeastcoders.pantroid.view;

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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Majfrendmartin on 2017-02-11.
 */

public class EditTypeFragment extends PresenterDialogFragment<EditTypeFragmentPresenter>
        implements EditTypeFragmentView {

    @BindView(R.id.et_type_name)
    EditText etTypeName;

    @Inject
    Context context;

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
                .pantryItemTypesModule(new PantryItemTypesModule())
                .build()
                .inject(this);

        presenter.bindView(this);
        onCreateAfterInjection(savedInstanceState);
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
        Toast.makeText(context, "Validation error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayDiscardChangesMessage() {

    }

    @Override
    public void displayTypeNotFoundErrorMessage() {

    }

    @Override
    public void displaySaveSucceedMessage() {
        Toast.makeText(context, "Item saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void displaySaveFailedMessage() {

    }

    @Override
    public void finish() {
        dismiss();
    }
}
