package com.peekay.aamirice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.peekay.aamirice.bean.BitmapSave;

import java.io.File;

import tools.SetTheme;

public class PicActivity extends AppCompatActivity {
    ImageView imageView;
    SharedPreferences sharedPreferences;
    SetTheme setTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE);
        setTheme = new SetTheme(this, this);
        setTheme.initTheme(sharedPreferences.getString("theme", "null"));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        imageView = findViewById(R.id.img_pic);
        imageView.setImageBitmap(BitmapSave.getBitmap());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(PicActivity.this).setTitle("是否保存图片到图库？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveImage();
                            }
                        }).show();
                return false;
            }
        });
    }

    //保存图片
    private void saveImage() {
        Bitmap bitmap = BitmapSave.getBitmap();
        if (MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "aamirice", "tion") == null) {
            Toast.makeText(this, "保存失败！请确认是否授予存储权限！", Toast.LENGTH_LONG).show();
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_STARTED, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));
            Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show();
        }
    }
}
