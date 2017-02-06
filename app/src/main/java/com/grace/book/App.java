package com.grace.book;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by dongjunkun on 2016/2/1.
 */
public class App extends Application {

    private static Context context;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;

        Logger.init("hhh")
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
