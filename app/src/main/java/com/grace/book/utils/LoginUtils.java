package com.grace.book.utils;

import android.app.Activity;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.grace.book.R;
import com.grace.book.dialog.LoginDialog;

public class LoginUtils {

    public static void showLoginDialog(Activity activity) {
        LoginDialog loginDialog = new LoginDialog(activity);
        Window dialogWindow = loginDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        dialogWindow.setAttributes(layoutParams);
        loginDialog.show();
    }
}
