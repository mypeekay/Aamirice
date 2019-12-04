package com.peekay.aamirice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.just.agentweb.AgentWeb;

import tools.SetTheme;

public class VideoPlayActivity extends AppCompatActivity {
    AgentWeb agentWeb;
    SetTheme setTheme;
    SharedPreferences sharedPreferences;
    LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE);
        setTheme = new SetTheme(this, this);
        setTheme.initTheme(sharedPreferences.getString("theme", "null"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = findViewById(R.id.view);
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra("uri"));
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
