package com.grace.book.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by chenxb
 * 17/2/22.
 */

public class KeyboardUtils {

    public static void showKeyboard(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {

        }
    }

    public static void hideKeyboard(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {

        }
    }
}
