package com.xlkx.testaidl.service_brocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private OnReceiverData onReceiverData;

    public void setOnReceiverData(OnReceiverData onReceiverData) {
        this.onReceiverData = onReceiverData;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onReceiverData != null) {
            onReceiverData.onReceive(intent.getStringExtra("data"));

        }
    }
}
