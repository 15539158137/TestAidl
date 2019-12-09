package com.xlkx.testaidl.service_brocast;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xlkx.testaidl.TimeUtil;
import com.xlkx.testaidl.beans.User;


public class Service_Bind extends Service {
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("service_普通", "onLowMemory");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        SharedPreferences sharedPreferences = getSharedPreferences("time2", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MyService_Time", TimeUtil.getNowData());
        editor.commit();
        Log.e("service_普通", "onTaskRemoved");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("service_普通", "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent1) {
        Log.e("建立绑定时传递过来的数据是", intent1.getStringExtra("data"));
        Log.e("service_普通", "onBind");
        received_data = "bind时候发的数据" + intent1.getStringExtra("data");

        //发送广播
        Intent intent = new Intent();
        intent.setAction("receiver");
        intent.putExtra("data", received_data);
        sendBroadcast(intent);
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("service_普通", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("service_普通gg", "onDestroy");
        super.onDestroy();
    }

    //activity发送数据给service
    public void sendDataToServer(String data) {
        received_data = data;
        received_data = "sendDataToServer发的数据" + data;

        //发送广播
        Intent intent = new Intent();
        intent.setAction("receiver");
        intent.putExtra("data", received_data);
        sendBroadcast(intent);
        Log.e("sendDataToServer收到了data", data);
    }

    private void receiveData(User user) {
        Log.e("MyService收到了user", user.toString());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //发送广播
        Intent intent1 = new Intent();
        intent1.setAction("receiver");
        intent1.putExtra("data", "onStartCommand收到的数据" + intent.getStringExtra("data"));
        sendBroadcast(intent1);
        Log.e("service_普通", "onStartCommand");
        Log.e("startservice_普通收到了参数", intent.getStringExtra("data"));
        SharedPreferences sharedPreferences = getSharedPreferences("time2", MODE_PRIVATE);
        Log.e("服务_普通上次关闭的时间", sharedPreferences.getString("MyService_Time", "null"));
        return super.onStartCommand(intent, flags, startId);
    }

    String received_data = "默认数据";
    MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder {
        //activity发送数据给service
        public void setData(String data) {
            received_data = "setData方式发的数据" + data;

            //发送广播
            Intent intent = new Intent();
            intent.setAction("receiver");
            intent.putExtra("data", received_data);
            sendBroadcast(intent);
            Log.e("mybind_setData收到了data", data);
        }

        public Service_Bind getService() {
            return Service_Bind.this;
        }
    }
}
