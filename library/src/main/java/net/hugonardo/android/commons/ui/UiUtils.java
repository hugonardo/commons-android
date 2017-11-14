package net.hugonardo.android.commons.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiUtils {

    public static void hideKeyboard(Activity activity, int flags) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        View viewWithFocus = activity.getCurrentFocus();
        IBinder windowToken = viewWithFocus != null ? viewWithFocus.getWindowToken() : null;

        if (windowToken != null) {
            imm.hideSoftInputFromWindow(windowToken, flags);
        }
    }

    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(Fragment fragment, int flags) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            hideKeyboard(activity, flags);
        }
    }

    public static void hideKeyboard(Fragment fragment) {
        hideKeyboard(fragment, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(android.support.v4.app.Fragment fragment, int flags) {
        Activity activity = fragment.getActivity();
        if (activity != null) {
            hideKeyboard(activity, flags);
        }
    }

    public static void hideKeyboard(android.support.v4.app.Fragment fragment) {
        hideKeyboard(fragment, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
