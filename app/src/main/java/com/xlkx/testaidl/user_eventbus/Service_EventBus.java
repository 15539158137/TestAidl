package com.xlkx.testaidl.user_eventbus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xlkx.testaidl.beans.User;

import org.greenrobot.eventbus.EventBus;

public class Service_EventBus extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e("延迟1秒异常",e.getMessage()+"");
            e.printStackTrace();
        }finally {
            Log.e("延迟1秒","结束");
            EventBus.getDefault().postSticky(new User("我是服务端发出的数据"));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
