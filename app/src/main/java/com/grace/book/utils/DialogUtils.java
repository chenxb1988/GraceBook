package com.grace.book.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by DuoNuo on 2017/2/15.
 */
public class DialogUtils {
    private static AlertDialog mAlertDialog;

    public static void showProgress(Context context, String msg) {
        showProgress(context, msg, false);
    }

    public static void showProgress(Context context, String msg, boolean cancelable) {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        } else {
            mAlertDialog = new AlertDialog.Builder(context).create();
        }
        mAlertDialog.setMessage(msg);
        mAlertDialog.setCancelable(cancelable);
        mAlertDialog.setCanceledOnTouchOutside(cancelable);
        mAlertDialog.show();
    }

    public static void dismissProgress() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
