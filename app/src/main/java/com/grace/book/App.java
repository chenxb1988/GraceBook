package com.grace.book;

import android.app.Application;
import android.content.Context;

import com.grace.book.utils.ConstData;
import com.grace.book.utils.SharedUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by chenxb on 2017/2/1.
 */
public class App extends Application {

    private static Context context;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        SharedUtils.init(this);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                ConstData.DEVICE_TOKEN = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        Logger.init(ConstData.APP_NAME)
                .methodOffset(2)
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);

    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return instance;
    }
}
