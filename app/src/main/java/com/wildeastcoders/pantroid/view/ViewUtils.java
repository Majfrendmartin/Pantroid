package com.wildeastcoders.pantroid.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wildeastcoders.pantroid.model.event.DialogButtonClickedEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Majfrendmartin on 2017-02-15.
 */

public class ViewUtils {

    private ViewUtils() {
        //no-op
    }

    public static void showConfirmationAlertDialog(@NonNull FragmentManager fragmentManager,
                                                   @StringRes int titleId, @StringRes int messageId,
                                                   int dialogId, @NonNull String dialogTag) {
        final ConfirmationDialogFragment fragment =
                ConfirmationDialogFragment.newInstance(titleId, messageId, dialogId);
        showDialogFragment(fragment, fragmentManager, dialogTag);
    }

    public static void postDialogButtonClicked(int dialogId, int buttonId) {
        EventBus.getDefault().post(new DialogButtonClickedEvent(dialogId, buttonId));
    }

    public static void showDialogFragment(@NonNull DialogFragment fragment, @NonNull FragmentManager fragmentManager, @NonNull String tag) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        fragment.show(fragmentManager, tag);
    }
}
