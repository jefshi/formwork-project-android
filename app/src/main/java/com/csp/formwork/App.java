package com.csp.formwork;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Application
 * Created by csp on 2018/7/9 009.
 */
public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
    }
}
