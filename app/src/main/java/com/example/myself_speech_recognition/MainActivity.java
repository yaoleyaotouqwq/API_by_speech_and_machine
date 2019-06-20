package com.example.myself_speech_recognition;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

    }

    //进入语音识别
    public void speech_recognizer(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Speech_initActivity.class);
        startActivity(intent);
    }
    //进入机器人聊天
    public void machine_commonion(View v){
        Toast.makeText(getApplicationContext(), "已连接到小菲菲同学",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Machine_communionActivity.class);
        startActivity(intent);
    }
}
