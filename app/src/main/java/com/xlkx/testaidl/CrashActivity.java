package com.xlkx.testaidl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.os.Process;
public class CrashActivity extends Activity {

    private TextView prompt , crashMessage;
    private String exceptionOfCrash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("CrashActivity","onCreate");
        setContentView(R.layout.activity_crash);
        initIntent();
        initView();
       // countDownTimer.start();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent == null)return;
        exceptionOfCrash = intent.getStringExtra("exceptionOfCrash");
        Log.e("收到的错误信息",exceptionOfCrash+"");
    }

    private void initView() {
        prompt = findViewById(R.id.prompt);
        crashMessage = findViewById(R.id.crashMessage);
        crashMessage.setText(exceptionOfCrash);
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Process.killProcess(Process.myPid());
                System.exit(1);
//                Log.e("收到点击事件","====");
//                startActivity(new Intent(CrashActivity.this,MainActivity.class));
            }
        });
    }
int count=1;
    private CountDownTimer countDownTimer = new CountDownTimer(10000 , 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            count++;
            prompt.setText(String.format("error" , "Warning!\nnuclear missile will be launched in %1s second" , millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            Log.e("finish","onFinish"+count);
           // ActivityCollector.finishAll();
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
