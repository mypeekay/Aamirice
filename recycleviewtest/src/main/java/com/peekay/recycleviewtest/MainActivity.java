package com.peekay.recycleviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Bitmaps> bitmaps = new ArrayList<>();
    RecyclerView recyclerView;
    BitmapAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        for (int i = 0; i < 555; i++) {
            bitmaps.add(new Bitmaps(R.drawable.ic_launcher_background));
            bitmaps.add(new Bitmaps(R.drawable.ic_close_black_24dp));
            bitmaps.add(new Bitmaps(R.drawable.a));
        }
        adapter = new BitmapAdapter(bitmaps);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        startActivity(new Intent(this,Main2Activity.class));
    }
}
