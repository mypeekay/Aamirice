package com.peekay.recycleviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    ListAdapter adapter;
    List<Bitmaps> bitmaps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.lv);
        for (int i = 0; i < 2; i++) {
            bitmaps.add(new Bitmaps(R.drawable.a));
            bitmaps.add(new Bitmaps(R.drawable.ic_launcher_background));
            bitmaps.add(new Bitmaps(R.drawable.a));
        }
        adapter = new ListAdapter(this, R.layout.item2, bitmaps);
        listView.setAdapter(adapter);
    }
}
