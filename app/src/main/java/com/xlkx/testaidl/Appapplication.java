package com.xlkx.testaidl;

import android.app.Application;

public class Appapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getCrashHander();
        crashHandler.init(this);
    }
}
