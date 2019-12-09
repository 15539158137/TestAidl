package com.xlkx.testaidl.service_brocast;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xlkx.testaidl.BaseActivity;
import com.xlkx.testaidl.R;

public class TestActivity_BinderService extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("receiver");
        MyBroadcastReceiver myBrcocast = new MyBroadcastReceiver();
        registerReceiver(myBrcocast, intentFilter);
        myBrcocast.setOnReceiverData(new OnReceiverData() {
            @Override
            public void onReceive(String data) {
                Log.e("收到了service发送过来的数据", "" + data);
            }
        });
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity_BinderService.this, Service_Bind.class);
                //可以一直startservice数据在onstartcommand里面拿
                intent.putExtra("data", "绑定时候时候传输的数据");
                startService(intent);

            }
        });
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity_BinderService.this, Service_Bind.class);
                //建立绑定的时候直接发送数据，这个数据在onbind的intent里面可以拿到
                intent.putExtra("data", "绑定时候时候传输的数据");
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);

            }
        });
        Button button = findViewById(R.id.start_service);
        button.setText("再发一次数据");
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBinder.setData("activity第二次通过mybind发送数据");
                myBinder.getService().sendDataToServer("sendDataToServer第二次发送数据");
            }
        });
    }

    //binderService
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (Service_Bind.MyBinder) service;
            myBinder.setData("activity通过mybind发送数据");
            myBinder.getService().sendDataToServer("sendDataToServer发送数据");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("服务断开", name.getClassName() + "");
        }
    };
    Service_Bind.MyBinder myBinder;

}