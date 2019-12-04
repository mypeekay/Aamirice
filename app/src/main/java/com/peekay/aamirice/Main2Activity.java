package com.peekay.aamirice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.peekay.aamirice.adapter.LeftListAdapter;
import com.peekay.aamirice.bean.LeftList;

import java.util.ArrayList;
import java.util.List;

import tools.SetTheme;


public class Main2Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ListView listView_left;
    DrawerLayout drawerLayout;
    SetTheme setTheme;
    LeftListAdapter adapter;
    List<LeftList> leftLists = new ArrayList<>();
    TextView textView_nickname, textView_sign;
    ImageView imageView_ava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE);
        setTheme = new SetTheme(this, this);
        setTheme.initTheme(sharedPreferences.getString("theme", "null"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//TODO 设置禁止截屏
        int permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        //判断是否有读写权限
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
        initView();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                View content = drawerLayout.getChildAt(0);//主页内容
                View menu = drawerView;//侧边栏
                float scale = 1 - slideOffset;//1~0
                float lefrScale = (float) (1 - 0.1 * scale);
                float rightScale = (float) (0.9f + 0.1 * scale);//0.7~1
                menu.setScaleY(lefrScale);//1~0.7
                menu.setScaleX(lefrScale);
                content.setScaleY(rightScale);
                content.setScaleX(rightScale);
                content.setTranslationX(menu.getMeasuredWidth() * slideOffset);//0~width
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    private void initView() {
        for (int i = 0; i < 10; i++) {
            leftLists.add(new LeftList(R.drawable.my, "个人中心"));
            leftLists.add(new LeftList(R.drawable.radio, "radio"));
            leftLists.add(new LeftList(R.drawable.shuffle, "shuffle"));
        }
        drawerLayout = findViewById(R.id.drawer);
        listView_left = findViewById(R.id.lv_left);
        adapter = new LeftListAdapter(this, R.layout.left_list, leftLists);
        listView_left.setAdapter(adapter);
        listView_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main2Activity.this, "老实说，这里还没做！", Toast.LENGTH_LONG).show();
            }
        });
        textView_nickname = findViewById(R.id.tv_nick_name);
        textView_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(Main2Activity.this);
                editText.setText(textView_nickname.getText().toString());
                new AlertDialog.Builder(Main2Activity.this).setTitle("临时修改NickName")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().isEmpty()) {
                                    textView_nickname.setText(editText.getText().toString());
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
        textView_sign = findViewById(R.id.tv_sign);
        textView_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(Main2Activity.this);
                editText.setText(textView_sign.getText().toString());
                new AlertDialog.Builder(Main2Activity.this).setTitle("临时修改Sign")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().isEmpty()) {
                                    textView_sign.setText(editText.getText().toString());
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
            }
        });
        imageView_ava = findViewById(R.id.img_login);
        imageView_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Main2Activity.this, "null", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        return NavHostFragment.findNavController(fragment).navigateUp();
    }

    //优化横竖屏切换问题
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onSaveInstanceState(outState, outPersistentState);
        }
    }
}
