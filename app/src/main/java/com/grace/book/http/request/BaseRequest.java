package com.grace.book.http.request;

import android.os.Build;

import com.grace.book.utils.ConstData;

/**
 * Created by chenxb
 * 17/2/6.
 */

public class BaseRequest {
    private String os;
    private String osVersion;
    private String appVersion;
    private String deviceId;
    private String deviceToken;

    public BaseRequest() {
        os = "Android";
        osVersion = Build.VERSION.RELEASE;
        appVersion = ConstData.APP_VERSION;
        deviceId = ConstData.DEVICE_ID;
        deviceToken = ConstData.DEVICE_TOKEN;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
