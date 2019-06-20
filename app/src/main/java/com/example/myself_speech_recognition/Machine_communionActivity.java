package com.example.myself_speech_recognition;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Machine_communionActivity extends AppCompatActivity {

    private TextView out_communion;
    private EditText input_words;
    private RelativeLayout communion_layout;
    private ScrollView scrollView_oper;
    private String final_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_communion);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        out_communion = findViewById(R.id.out_communion);
        input_words = findViewById(R.id.input_words);
        communion_layout = findViewById(R.id.communion_layout);
        scrollView_oper = findViewById(R.id.scrollView_oper);
        initData();

        input_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "请等一下小菲菲回话哦！",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initData(){
        out_communion.append("小菲菲:哈喽，咱们来聊天吧！");
    }

    public void communion(View v) {

        final String myquestion = input_words.getText().toString().trim();
        if (myquestion == null || "".equals(myquestion)) {
            Toast.makeText(getApplicationContext(), "请输入聊天信息",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            input_words.setFocusableInTouchMode(false);
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        input_words.setText("");

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                final_result=(String)msg.obj;
                out_communion.append("\nMe:"+myquestion+"\n小菲菲:"+final_result);
                input_words.setFocusableInTouchMode(true);
            }
        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                String result = inpututils.getString(myquestion);
                Looper.prepare();
                Message message=new Message();
                message.what=1;
                message.obj=result;
                handler.sendMessage(message);
                Looper.loop();

            }
        }).start();

        //scrollView_oper自动滑动到底部
        scrollView_oper.post(new Runnable() {
            public void run() {
                scrollView_oper.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });


    }

}
