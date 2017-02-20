package com.wildeastcoders.pantroid.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import static com.wildeastcoders.pantroid.view.ViewUtils.postDialogButtonClicked;

/**
 * Created by Majfrendmartin on 2017-02-15.
 */

public class ConfirmationDialogFragment extends DialogFragment {

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_MESSAGE = "KEY_MESSAGE";
    public static final String KEY_DIALOG_ID = "KEY_DIALOG_ID";

    public static final int BUTTON_POSITIVE = AlertDialog.BUTTON_POSITIVE;
    public static final int BUTTON_NEGATIVE = AlertDialog.BUTTON_NEGATIVE;
    public static final int BUTTON_NEUTRAL = AlertDialog.BUTTON_NEUTRAL;

    public static ConfirmationDialogFragment newInstance(@StringRes int title, @StringRes int message,
                                                         int dialogId) {
        final ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();

        final Bundle bundle = new Bundle(3);
        bundle.putInt(KEY_TITLE, title);
        bundle.putInt(KEY_MESSAGE, message);
        bundle.putInt(KEY_DIALOG_ID, dialogId);
        fragment.setArguments(bundle);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle arguments = getArguments();

        final int title = arguments.getInt(KEY_TITLE);
        final int message = arguments.getInt(KEY_MESSAGE);
        final int dialogId = arguments.getInt(KEY_DIALOG_ID);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final AlertDialog alertDialog = builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> postDialogButtonClicked(dialogId, BUTTON_POSITIVE))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> postDialogButtonClicked(dialogId, BUTTON_NEGATIVE))
                .create();
        return alertDialog;
    }
}
