package com.xlkx.testaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xlkx.testaidl.service_brocast.Service_Bind;
import com.xlkx.testaidl.service_brocast.MyService_Time;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        TextView textView=null;
//        textView.setText("");
        findViewById(R.id.to_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Service_Bind.class);
                intent.putExtra("data", "start_service传输的数据");
                startService(intent);


                Intent intent1 = new Intent(MainActivity.this, MyService_Time.class);
                intent1.putExtra("data", "start_service传输的数据");
                startService(intent1);



            }
        });
        findViewById(R.id.dis_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Service_Bind.class);
                stopService(intent);
            }
        });
        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Service_Bind.class);
                bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE);
                stopService(intent);
            }
        });
    }
    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder= (Service_Bind.MyBinder) service;
            myBinder.setData("1");
            myBinder.getService().sendDataToServer("发送数据");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("服务断开",name.getClassName()+"");
        }
    };
    Service_Bind.MyBinder myBinder;
}
