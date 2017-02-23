package com.grace.book.utils;

import com.grace.book.App;

/**
 * Created by chenxb on 17/2/13.
 */

public class ConstData {
    public static final String TOKEN = "user_token";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_AVATAR = "user_avatar";

    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";

    public static final String FELLOW_LIST = "fellow_list";
    public static final String FELLOW_NAME = "fellow_name";
    public static final String GROUP_LIST = "group_list_";

    public static String APP_VERSION;
    public static String DEVICE_ID;

    public static void initData() {
        APP_VERSION = AppHelper.getVersionName(App.getInstance());
        DEVICE_ID = AppHelper.getDeviceId(App.getInstance());
    }
}
