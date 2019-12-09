package com.xlkx.testaidl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    ActivityCollector.getInstance().addActivity(BaseActivity.this);
    }
}
