package com.grace.book.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.grace.book.R;

import es.dmoral.toasty.Toasty;


/**
 * Created by chenxb
 * 17/2/20.
 */

public class ToastUtils {
    public static void showSnack(Activity activity, String text) {
        try {
            ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(viewGroup, text, Snackbar.LENGTH_SHORT);
            View snackview = snackbar.getView();
            snackview.setBackgroundResource(ThemeUtils.getThemePrimaryColor(activity));
            ((TextView) snackview.findViewById(R.id.snackbar_text)).setTextColor(activity.getResources().getColor(R.color.white));
            snackbar.show();
        } catch (Exception e) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToasty(Context context, String text) {
        Toasty.custom(context, text, null,
                context.getResources().getColor(R.color.white),
                ThemeUtils.getThemePrimaryColor(context), 0, false, false).show();
    }

    public static void showErrorToasty(Context context, String text) {
        showCustom(context, text, es.dmoral.toasty.R.drawable.ic_clear_white_48dp);
    }

    public static void showSuccessToasty(Context context, String text) {
        showCustom(context, text, es.dmoral.toasty.R.drawable.ic_check_white_48dp);
    }

    public static void showInfoToasty(Context context, String text) {
        showCustom(context, text, es.dmoral.toasty.R.drawable.ic_info_outline_white_48dp);
    }

    public static void showWarningToasty(Context context, String text) {
        showCustom(context, text, es.dmoral.toasty.R.drawable.ic_error_outline_white_48dp);
    }

    private static void showCustom(Context context, String text, int iconId) {
        Toasty.custom(context, text, iconId,
                context.getResources().getColor(R.color.white),
                ThemeUtils.getThemePrimaryColor(context), 0, true, true).show();
    }
}
