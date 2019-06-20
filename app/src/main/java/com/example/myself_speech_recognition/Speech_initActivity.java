package com.example.myself_speech_recognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Speech_initActivity extends SpeechActivity {
    private TextView view_content;
    private Button speech_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_recognizer);

        ArrayList<String> ApplyList = new ArrayList<>();

        String requests[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        //检查是否有权限
        for (String request : requests)
        {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat
                    .checkSelfPermission(this, request))
            {
                //没有就加
                ApplyList.add(request);
            }
        }

        //在这里请求所缺权限
        if (!ApplyList.isEmpty())
        {
            String[] toApplys = new String[ApplyList.size()];
            ApplyList.toArray(toApplys);
            ActivityCompat.requestPermissions(this, toApplys,
                    1000);
        }

        view_content = findViewById(R.id.tips);//TextView
        speech_bottom = findViewById(R.id.speak);//Bottom

        speech_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发语音识别并显示在textView上
                out_speech_word(new AsyncCallback<String>(){
                        @Override
                        public void onResult(String s)
                        {
                            view_content.setText(s);
                        }
                    }
                );
            }
        });

    }

    public interface AsyncCallback<Result>
    {
        @UiThread
        void onResult(Result result);
    }

    public void Speechreturn(View v) {
        Intent intent = new Intent();
        intent.setClass(Speech_initActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
