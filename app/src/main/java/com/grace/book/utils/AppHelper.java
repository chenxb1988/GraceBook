package com.grace.book.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.grace.book.App;

/**
 * 应用程序辅助类
 *
 */
public class AppHelper {


    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    /**
     * 获取当前版本号
     *
     * @return versionCode，失败返回0
     */
    public static int getVersionCode(Context context) {
        int code = 0;
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            code = packInfo.versionCode;
        }
        return code;
    }

    /**
     * 获取当前版本名
     *
     * @return versionName，失败返回""
     */
    public static String getVersionName(Context context) {
        String name = "";
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            name = packInfo.versionName;
        }
        return name;
    }

    /**
     * 复制文字
     *
     * @param context
     * @param text
     */
    public static void copyText(Context context, String text) {
        if (context != null && text != null) {
            ClipboardManager cbm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cbm.setText(text);
        }
    }

    /**
     * 隐藏软键盘(貌似当前软键盘已经关闭的话也没关系)
     *
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static Bitmap getNinePatch(int id, int x, int y, Context context) {
        // id is a resource id for a valid ninepatch
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

        byte[] chunk = bitmap.getNinePatchChunk();
        NinePatchDrawable np_drawable = new NinePatchDrawable(bitmap, chunk, new Rect(), null);
        np_drawable.setBounds(0, 0, x, y);

        Bitmap output_bitmap = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output_bitmap);
        np_drawable.draw(canvas);

        return output_bitmap;
    }

    private static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 显示信息，如屏幕宽度、高度等
     */
    public static DisplayMetrics displayInfo(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }

    /**
     * 启动App
     *
     * @param context
     * @param packageName
     */
    public static void launchApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //
        }
    }

    public static boolean isTaskStopped(AsyncTask task) {
        return task == null || task.getStatus() == AsyncTask.Status.FINISHED || task.isCancelled();
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    //获取设备唯一imei
    public static String getDeviceId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

}
