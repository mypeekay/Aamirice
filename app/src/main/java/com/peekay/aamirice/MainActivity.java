package com.peekay.aamirice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import tools.SetTheme;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                    finish();
            }
        }
    };
    SharedPreferences sharedPreferences;
    SetTheme setTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme=new SetTheme(this,this);
        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE);
        setTheme.initTheme(sharedPreferences.getString("theme", "null"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
