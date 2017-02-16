package com.grace.book.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.grace.book.App;
import com.grace.book.entity.LoginInfo;
import com.grace.book.widget.theme.Theme;


public class SharedUtils {
    private static SharedPreferences mSharePreferences;
    private static SharedPreferences.Editor mEditor;

    public static void init(App app) {
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
        mEditor = mSharePreferences.edit();
    }

    public static boolean isFirstTime(String key) {
        if (getBoolean(key, false)) {
            return false;
        } else {
            putBoolean(key, true);
            return true;
        }
    }

    public static boolean contains(String key) {
        return mSharePreferences.contains(key);
    }

    public static int getInt(final String key, final int defaultValue) {
        return mSharePreferences.getInt(key, defaultValue);
    }

    public static boolean putInt(final String key, final int pValue) {
        mEditor.putInt(key, pValue);
        return mEditor.commit();
    }

    public static long getLong(final String key, final long defaultValue) {
        return mSharePreferences.getLong(key, defaultValue);
    }

    public static Long getLong(final String key, final Long defaultValue) {
        if (mSharePreferences.contains(key)) {
            return mSharePreferences.getLong(key, 0);
        } else {
            return null;
        }
    }


    public static boolean putLong(final String key, final long pValue) {
        final SharedPreferences.Editor editor = mSharePreferences.edit();

        editor.putLong(key, pValue);

        return editor.commit();
    }

    public static boolean getBoolean(final String key, final boolean defaultValue) {
        return mSharePreferences.getBoolean(key, defaultValue);
    }

    public static boolean putBoolean(final String key, final boolean pValue) {
        final SharedPreferences.Editor editor = mSharePreferences.edit();

        editor.putBoolean(key, pValue);

        return editor.commit();
    }

    public static String getString(final String key) {
        return mSharePreferences.getString(key, "");
    }

    public static String getString(final String key, final String defaultValue) {
        return mSharePreferences.getString(key, defaultValue);
    }

    public static boolean putString(final String key, final String pValue) {
        final SharedPreferences.Editor editor = mSharePreferences.edit();

        editor.putString(key, pValue);

        return editor.commit();
    }


    public static boolean remove(final String key) {
        final SharedPreferences.Editor editor = mSharePreferences.edit();

        editor.remove(key);

        return editor.commit();
    }

    public static Theme getCurrentTheme(Context context) {
        return Theme.valueOf(SharedUtils.getString("app_theme", Theme.Indigo.name()));
    }

    public static void setCurrentTheme(Theme currentTheme) {
        SharedUtils.putString("app_theme", currentTheme.name());
    }

    public static void saveUserData(LoginInfo loginInfo) {
        SharedUtils.putString(ConstData.TOKEN, loginInfo.getAuthToken());
        SharedUtils.putString(ConstData.USER_ID, loginInfo.getUserId());
        SharedUtils.putString(ConstData.USER_NAME, loginInfo.getUserName());
        SharedUtils.putString(ConstData.USER_AVATAR, loginInfo.getAvatar());
    }

    public static void clearUserData() {
        SharedUtils.putString(ConstData.TOKEN, "");
        SharedUtils.putString(ConstData.USER_ID, "");
        SharedUtils.putString(ConstData.USER_NAME, "");
        SharedUtils.putString(ConstData.USER_AVATAR, "");
    }
}