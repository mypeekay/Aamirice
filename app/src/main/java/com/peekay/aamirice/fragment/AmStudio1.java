package com.peekay.aamirice.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.peekay.aamirice.R;
import com.peekay.aamirice.adapter.AmStudioBitmapAdapter;
import com.peekay.aamirice.bean.AmStudioBitmaps;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tools.SetTheme;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class AmStudio1 extends Fragment {
    View view;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipeRefreshLayout;
    JSONArray jsonArray;
    AmStudioBitmapAdapter adapter;
    List<AmStudioBitmaps> bitmaps = new ArrayList<>();
    List<String> string = new ArrayList<>();
    RecyclerView recyclerView;
    TextView textView_tip;
    Response response;
    ExecutorService executorService;
    OkHttpClient client;
    Request request;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            adapter.notifyDataSetChanged();
            if (bitmaps.size() > string.size() - 1) {
                bitmaps.add(new AmStudioBitmaps(BitmapFactory.decodeResource(getResources(), R.drawable.loadok)));
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.amstudio1, null);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycle_amstudio1);
        swipeRefreshLayout = view.findViewById(R.id.swipe_amstudio1);
        textView_tip = view.findViewById(R.id.tv_tips);
        executorService=new ScheduledThreadPoolExecutor(5);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (bitmaps.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client = new OkHttpClient.Builder().build();
                            request = new Request.Builder().url("https://ampk119-1255693066.cos.ap-chongqing.myqcloud.com/AamiricePhone/%E5%9B%BE%E9%9B%86/amstudio2.txt").build();
                            try {
                                response = client.newCall(request).execute();
                                jsonArray = new JSONArray(response.body().string());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    string.add(jsonArray.getString(i));
                                }
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        new GetNetPic(string).execute();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    textView_tip.setVisibility(View.GONE);
                } else {
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        textView_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.shutdownNow();
            }
        });
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AmStudioBitmapAdapter(getActivity(), getContext(), bitmaps, R.layout.recycleitem_amstudio);
        recyclerView.setAdapter(adapter);
        SetTheme setTheme = new SetTheme(swipeRefreshLayout);
        sharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        setTheme.initSwipe(sharedPreferences.getString("theme", "null"));
    }

    class GetNetPic extends AsyncTask<String, Integer, Bitmap> {
        Bitmap bitmap;
        List<String> string;

        public GetNetPic(List<String> string) {
            this.string = string;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            for (int i = 0; i < string.size(); i++) {
                client = new OkHttpClient.Builder().build();
                request = new Request.Builder().url(string.get(i)).build();
                try {
                    response = client.newCall(request).execute();
                    bitmaps.add(new AmStudioBitmaps(BitmapFactory.decodeStream(response.body().byteStream())));
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }
    }


    //一键保存图片
    private void saveImage() {
        int s = 0;
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bitmap = bitmaps.get(i).getBitmap();
            if (MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "" + i, "amstudio") == null) {
            } else {
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_STARTED, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));
                s++;
            }
        }
        Looper.prepare();
        Toast.makeText(getContext(), "总共" + bitmaps.size() + "张图片，已成功保存" + s + "张图片!", Toast.LENGTH_LONG).show();
    }
}
