package com.xlkx.testaidl.user_eventbus;

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
import com.xlkx.testaidl.beans.User;
import com.xlkx.testaidl.service_brocast.MyBroadcastReceiver;
import com.xlkx.testaidl.service_brocast.OnReceiverData;
import com.xlkx.testaidl.service_brocast.Service_Bind;
import com.xlkx.testaidl.service_brocast.TestActivity_BinderService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TestActivity_EventBus extends BaseActivity {
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(User eventMessage) {
        Log.e("收到event", eventMessage.toString());
        Button button = findViewById(R.id.start_service);
        button.setText("收到了eventbus");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {

        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity_EventBus.this, Service_EventBus.class);
                //可以一直startservice数据在onstartcommand里面拿
                intent.putExtra("data", "绑定时候时候传输的数据");
                startService(intent);

            }
        });
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity_EventBus.this, Service_EventBus.class);
                //建立绑定的时候直接发送数据，这个数据在onbind的intent里面可以拿到
                intent.putExtra("data", "绑定时候时候传输的数据");
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);

            }
        });

    }

    //binderService
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("服务断开", name.getClassName() + "");
        }
    };

}