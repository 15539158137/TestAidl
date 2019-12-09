package com.xlkx.testaidl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

import com.xlkx.testaidl.service_brocast.Service_Bind;

public class TwoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorview = getWindow().getDecorView();
        FrameLayout content = decorview.findViewById(android.R.id.content);
        content.removeAllViews();
        Intent intent = new Intent(TwoActivity.this, Service_Bind.class);
        stopService(intent);
    }
}
