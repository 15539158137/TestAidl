package com.xlkx.testaidl.service_brocast;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xlkx.testaidl.TimeUtil;

public class MyService_Time extends Service {
    @Override
    public void onLowMemory() {

        super.onLowMemory();
        Log.e("service_时间","onLowMemory");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MyService_Time_Time", TimeUtil.getNowData());
        editor.commit();
        Log.e("service_时间","onTaskRemoved");
    }
    @Override
    public void onCreate() {

        super.onCreate();
        Log.e("service_时间", "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("service_时间", "onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("service_时间", "onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("service_时间服务", "onStartCommand");
        Log.e("startservice时间服务收到了参数", intent.getStringExtra("data"));
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        Log.e("服务上次关闭的时间", sharedPreferences.getString("MyService_Time_Time", "null"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("service_时间", "onDestroy0");
        super.onDestroy();

        Log.e("service_时间", "onDestroy");
    }
    MyBinder myBinder=new MyBinder();
    class MyBinder extends Binder{
        public MyService_Time getService(){
            return MyService_Time.this;
        }
    }
}
