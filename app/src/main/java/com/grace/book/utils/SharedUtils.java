package com.grace.book.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.grace.book.MyApplication;
import com.grace.book.http.response.FellowListResponse;
import com.grace.book.http.response.GroupListResponse;
import com.grace.book.http.response.LoginInfo;
import com.grace.book.widget.theme.Theme;

import me.xiaopan.java.lang.StringUtils;


public class SharedUtils {
    private static SharedPreferences mSharePreferences;
    private static SharedPreferences.Editor mEditor;
    private static Gson Gson = new Gson();

    public static void init(MyApplication app) {
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
        return Theme.valueOf(getString("app_theme", Theme.Indigo.name()));
    }

    public static void setCurrentTheme(Theme currentTheme) {
        putString("app_theme", currentTheme.name());
    }

    public static void saveUserData(LoginInfo loginInfo) {
        putString(ConstData.TOKEN, loginInfo.getAuthToken());
        putString(ConstData.USER_ID, loginInfo.getUserId());
        putString(ConstData.USER_NAME, loginInfo.getUserName());
        putString(ConstData.USER_AVATAR, loginInfo.getAvatar());
    }

    public static void clearUserData() {
        putString(ConstData.TOKEN, "");
        putString(ConstData.USER_ID, "");
        putString(ConstData.USER_NAME, "");
        putString(ConstData.USER_AVATAR, "");
    }

    public static String getUserToken() {
        return getString(ConstData.TOKEN, "");
    }

    public static String getUserId() {
        return getString(ConstData.USER_ID, "");
    }

    public static String getUserName() {
        return getString(ConstData.USER_NAME, "");
    }

    public static String getUserAvatar() {
        return getString(ConstData.USER_AVATAR, "");
    }

    public static void saveFellowName(String name) {
        putString(ConstData.FELLOW_NAME, name);
    }

    public static String getFellowName() {
        String name = getString(ConstData.FELLOW_NAME);
        if (StringUtils.isEmpty(name)) {
            return "选择团契";
        }
        return name;
    }

    public static boolean isFellowNameNull() {
        String name = getString(ConstData.FELLOW_NAME);
        return StringUtils.isEmpty(name);
    }

    public static void saveFellowList(FellowListResponse response) {
        putString(ConstData.FELLOW_LIST, Gson.toJson(response));
    }

    public static FellowListResponse getFellowList() {
        String json = getString(ConstData.FELLOW_LIST);
        FellowListResponse response = Gson.fromJson(json, FellowListResponse.class);
        return response == null ? new FellowListResponse() : response;
    }

    public static void saveGroupList(String fellowId, GroupListResponse response) {
        putString(ConstData.GROUP_LIST + fellowId, Gson.toJson(response));
    }

    public static GroupListResponse getGroupList(String fellowId) {
        String json = getString(ConstData.GROUP_LIST + fellowId);
        GroupListResponse response = Gson.fromJson(json, GroupListResponse.class);
        return response == null ? new GroupListResponse() : response;
    }
}