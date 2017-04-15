package com.wildeastcoders.pantroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.wildeastcoders.pantroid.R;
import com.wildeastcoders.pantroid.view.ConfirmationDialogFragment;

import org.robolectric.shadows.ShadowToast;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static com.wildeastcoders.pantroid.view.activity.EditItemActivity.ABANDON_CHANGES_DIALOG_TAG;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Majfrendmartin on 2016-11-18.
 */

public abstract class TestUtils {

    public static final String MESSAGE_VIEW = "message";
    public static final String ALERT_TITLE = "alertTitle";

    private TestUtils() {
        //no-op
    }

    public static final void setupRxAndroid() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    public static final void tearDownRxAndroid() {
        RxAndroidPlugins.getInstance().reset();
    }

    public static final void setupTestRunner() {
        RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }

            @Override
            public Scheduler getNewThreadScheduler() {
                return Schedulers.immediate();
            }

            @Override
            public Scheduler getComputationScheduler() {
                return Schedulers.immediate();
            }
        });
    }


    @Nullable
    public static String getDialogTitle(@NonNull Resources resources, @NonNull Dialog dialog) {
        return getString(resources, dialog, ALERT_TITLE);
    }
    @Nullable
    public static String getDialogMessage(@NonNull Resources resources, @NonNull Dialog dialog) {
        return getString(resources, dialog, MESSAGE_VIEW);
    }

    @Nullable
    private static String getString(@NonNull Resources resources, @NonNull Dialog dialog, String viewName) {
        final int viewId = resources.getIdentifier(viewName, "id", "android");
        if (viewId > 0) {
            final TextView textView = (TextView) dialog.findViewById(viewId);
            if (textView != null) {
                return textView.getText().toString();
            }
        }
        return null;
    }

    public static void assertConfirmationDialogDisplayed(@NonNull Resources resources, @NonNull FragmentManager fragmetManager, @NonNull String tag) {

        final String expectedTitle = resources.getString(R.string.abandon_changes_dialog_title);
        final String expectedMessage = resources.getString(R.string.abandon_changes_dialog_message);

        assertConfirmationDialogDisplayed(resources, fragmetManager, tag, expectedTitle, expectedMessage);
    }

    public static void assertConfirmationDialogDisplayed(@NonNull Resources resources, @NonNull FragmentManager fragmetManager, @NonNull String tag, String expectedTitle, String expectedMessage) {
        assertNotNull(fragmetManager);
        assertFalse(TextUtils.isEmpty(tag));
        final Fragment fragment = fragmetManager.findFragmentByTag(tag);
        assertTrue(fragment instanceof ConfirmationDialogFragment);
        final ConfirmationDialogFragment confirmationDialogFragment = (ConfirmationDialogFragment) fragment;
        final String dialogMessage = TestUtils.getDialogMessage(resources, confirmationDialogFragment.getDialog());
        assertEquals(expectedMessage, dialogMessage);
// TODO:  test dialog title
//        final String dialogTitle = TestUtils.getDialogTitle(resources, confirmationDialogFragment.getDialog());
//        assertEquals(expectedTitle, dialogTitle);
    }

    public static void assertToastDisplayed(Context context, int edit_type_type_not_found_text) {
        final String textOfLatestToast = ShadowToast.getTextOfLatestToast();
        final String expectedString = context.getString(edit_type_type_not_found_text);
        assertEquals(expectedString, textOfLatestToast);
    }
}
