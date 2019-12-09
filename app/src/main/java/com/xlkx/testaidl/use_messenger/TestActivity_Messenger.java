package com.xlkx.testaidl.use_messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xlkx.testaidl.BaseActivity;
import com.xlkx.testaidl.R;
import com.xlkx.testaidl.beans.User;

public class TestActivity_Messenger extends BaseActivity {
    public static class MessenerHandler extends Handler {
        @Override
        public void handleMessage(Message ms1g) {
            switch (ms1g.what) {
                case 100:
                    Log.e("收到数据", ms1g.getData().getString("data"));
                    Log.e("收到数据_user", ms1g.getData().getSerializable("user").toString());
                    break;
                default:
                    break;
            }
        }
    }

    //客户端的messenger实例
    private Messenger messenger_client = new Messenger(new MessenerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TestActivity_Messenger.this, MessengerService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                stopService(intent);
            }
        });
        Button button = findViewById(R.id.start_service);
        button.setText("再发一次数据");
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain(null, 1);
                Bundle bundle = new Bundle();
                User user = new User("我是第二次发的user");
                bundle.putSerializable("user", user);
                bundle.putString("data", "我是第二次发的数据");
                message.setData(bundle);
                message.replyTo = messenger_client;
                try {
                    //向服务端发送消息
                    messenger_server.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //binderService
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到service里面的Messenger实例
            messenger_server = new Messenger(service);
            //创建消息，通过Bundle传递数据
            Message message = Message.obtain(null, 1);
            Bundle bundle = new Bundle();
            User user = new User("我是第一次发的user");
            bundle.putSerializable("user", user);
            bundle.putString("data", "发送数据");
            message.setData(bundle);
            //让服务端知道回复消息该给谁
            message.replyTo = messenger_client;
            try {
                //向服务端发送消息
                messenger_server.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("服务断开", name.getClassName() + "");
        }
    };
    //服务端的messenger实例
    Messenger messenger_server;

}
