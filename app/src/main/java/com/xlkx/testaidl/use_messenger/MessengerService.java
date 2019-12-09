package com.xlkx.testaidl.use_messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xlkx.testaidl.beans.User;

public class MessengerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //返回里面的handle给activity
        return messenger_server.getBinder();
    }

    //存放从activity接受到的数据，处理完后发送给activity
    public String receivedData = "";
    User user;

    public class MessenerHandler extends Handler {
        @Override
        public void handleMessage(Message ms1g) {
            switch (ms1g.what) {
                case 1:
                    //接受到activity发送过来的数据，处理区分下，然后发送给activity
                    Log.e("收到数据", ms1g.getData().getString("data"));
                    user = (User) ms1g.getData().getSerializable("user");
                    user.setName("返回数据:" + user.getName());
                    receivedData = ms1g.getData().getString("data");
                    //拿到activity的messenger对象
                    Messenger client = ms1g.replyTo;
                    //继续使用message通信
                    Message msg = Message.obtain(null, 100);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", user);
                    bundle.putString("data", "回复消息:" + receivedData);
                    msg.setData(bundle);
                    try {
                        //发送给客户端
                        client.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    //service端的Messenger
    private Messenger messenger_server = new Messenger(new MessenerHandler());

}
