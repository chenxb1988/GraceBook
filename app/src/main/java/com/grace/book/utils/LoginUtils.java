package com.grace.book.utils;

import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.grace.book.base.BaseActivity;
import com.grace.book.dialog.LoginDialog;

import me.xiaopan.java.lang.StringUtils;

public class LoginUtils {
    public static boolean isLogin(BaseActivity activity) {
        if (StringUtils.isEmpty(SharedUtils.getUserToken())) {
            ToastUtils.showInfoToasty(activity, "请先登录");
            showLoginDialog(activity);
            return false;
        }
        return true;
    }

    public static void showLoginDialog(BaseActivity activity) {
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
