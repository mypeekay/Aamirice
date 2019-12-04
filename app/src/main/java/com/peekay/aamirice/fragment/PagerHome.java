package com.peekay.aamirice.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannedString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.peekay.aamirice.R;
import com.peekay.aamirice.VideoPlayActivity;
import com.peekay.aamirice.adapter.LVContentAdapter;
import com.peekay.aamirice.adapter.ViewPagerAdapter;
import com.peekay.aamirice.bean.Content;
import com.peekay.aamirice.bean.UpdateJson;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tools.MyOpenHelper;
import tools.SetTheme;

public class PagerHome extends Fragment {
    //TODO 版本号，更新时修改
    private static final int version = 10;
    View view, pagerlayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView_content;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LVContentAdapter lvContentAdapter;
    private ScheduledExecutorService scheduledExecutorService;
    private List<Content> contents = new ArrayList<>();
    int index;//pager自动滑动的页数
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps1 = new ArrayList<>();//bannner
    private ArrayList<String> bitmapslink = new ArrayList<>();//banner点击链接
    private UpdateJson updateJson = new UpdateJson();
    private SearchView searchView_search;
    private ListView listView_search;
    private ArrayList<String> moviename = new ArrayList<>();//movie名字
    private ArrayList<String> movie = new ArrayList<>();//临时movie
    private ArrayAdapter<String> adapter;
    MyOpenHelper myOpenHelper;
    Response response;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    //加载公告
                    listView_content.setAdapter(lvContentAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    if (index == bitmaps1.size()) {
                        index = 0;
                    } else if (index == 0) {
                        index = bitmaps1.size() - 1;
                    }
                    viewPager.setCurrentItem(index);
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_home, null);
        initView();
        lvContentAdapter = new LVContentAdapter(getContext(), R.layout.contentlist, contents);
        lvContentAdapter.notifyDataSetChanged();
        bitmaps1.add(BitmapFactory.decodeResource(getResources(), R.drawable.loading));
        bitmapslink.add("");
        viewPagerAdapter = new ViewPagerAdapter(getContext(), bitmaps1, bitmapslink);
        return view;
    }

    public void initView() {
        myOpenHelper = new MyOpenHelper(getContext());
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.rgb(0, 150, 136), Color.rgb(219, 68, 55), Color.rgb(40, 40, 40));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(1);
            }
        });
        pagerlayout = LayoutInflater.from(getContext()).inflate(R.layout.pager, null);
        viewPager = pagerlayout.findViewById(R.id.viewPagerlayout);
        viewPager.addOnPageChangeListener(new ViewPageChange());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(20);
        viewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bitmapslink.get(index)));
                startActivity(intent);
            }
        });
        listView_content = view.findViewById(R.id.lv_content);
        listView_content.addHeaderView(pagerlayout);
        searchView_search = view.findViewById(R.id.search);
        searchView_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listView_search.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView_search.setVisibility(View.VISIBLE);
                if (newText.isEmpty()) {
                    adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, moviename);
                    listView_search.setAdapter(adapter);
                } else {
                    Cursor cursor = myOpenHelper.likeInfo(newText);
                    movie.clear();
                    while (cursor != null && cursor.moveToNext()) {
                        movie.add(cursor.getString(cursor.getColumnIndex("MOVIENAME")));
                    }
                    adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, movie);
                    listView_search.setAdapter(adapter);
                }
                return false;
            }
        });
        searchView_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView_search.setVisibility(View.GONE);
                adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, moviename);
                listView_search.setAdapter(adapter);
                return false;
            }
        });
        listView_search = view.findViewById(R.id.lv_search);
        listView_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String moviename = "", year = "", link = "", intro = "";
                moviename = listView_search.getItemAtPosition(position).toString();
                Cursor cursor = myOpenHelper.getMovie(moviename);
                while (cursor != null && cursor.moveToNext()) {
                    year = cursor.getString(cursor.getColumnIndex("YEAR"));
                    link = cursor.getString(cursor.getColumnIndex("LINK"));
                    intro = cursor.getString(cursor.getColumnIndex("INTRO"));
                }
                final String finalLink = link;
                AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle(moviename)
                        .setMessage("上映年份：" + year + "\n介绍：" + intro)
                        .setPositiveButton("播放视频", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (finalLink.equals("NULL") || finalLink.isEmpty()) {
                                    Toast.makeText(getContext(), "暂无链接！或尝试清空数据库并重新搜索", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                                    intent.putExtra("uri", finalLink);
                                    getActivity().startActivity(intent);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setNeutralButton("浏览器打开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(finalLink));
                                startActivity(intent);
                            }
                        }).show();
            }
        });
        Cursor cursor = myOpenHelper.getALLMovie();
        while (cursor != null && cursor.moveToNext()) {
            moviename.add(cursor.getString(cursor.getColumnIndex("MOVIENAME")));
        }
        adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, moviename);
        listView_search.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView_search.setTextFilterEnabled(true);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        SetTheme setTheme = new SetTheme(swipeRefreshLayout);
        setTheme.initSwipe(sharedPreferences.getString("theme", "null"));
    }

    @Override
    public void onResume() {
        super.onResume();
//        listView_search.setVisibility(View.GONE);
    }

    //TODO 加载更新，公告,banner的Json数据
    public void getData() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://ampk119-1255693066.cos.ap-chongqing.myqcloud.com/AamiricePhone/update.json")
                .get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String content = response.body().string();
                JxJson(content);
            }
        });

        //解析json获取banner图片地址
        client = new OkHttpClient.Builder().build();
        request = new Request.Builder().get().url("https://ampk119-1255693066.cos.ap-chongqing.myqcloud.com/AamiricePhone/pic.json").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                JxPicJson(response.body().string());
            }
        });
    }

    //TODO 解析json获取更新等内容
    public void JxJson(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int v;//版本比较
        String v1;//版本
        String url;//下载地址
        String content;//更新内容
        String time;//更新时间
        String post;//公告
        v = jsonObject.optInt("version");
        v1 = jsonObject.optString("version2");
        url = jsonObject.optString("downloadurl");
        content = jsonObject.optString("updatecontent");
        time = jsonObject.optString("updatetime");
        post = jsonObject.optString("公告");
        updateJson = new UpdateJson(v, v1, url, content, time, post);
        initNet();
    }

    //TODO 解析JSON获取banner地址
    public void JxPicJson(String data) {
        ArrayList<String> strings = new ArrayList<>();
        int num = 0;
        try {
            JSONObject jsonObject = new JSONObject(data);
            num = jsonObject.optInt("num");
            for (int i = 1; i <= num; i++) {
                strings.add(jsonObject.optString("pic" + i));
                bitmapslink.add(jsonObject.optString("link" + i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < strings.size(); i++) {
            new GetNetPic(strings.get(i)).execute();
        }
    }

    //TODO 加载公告，检测更新
    public void initNet() {
        int v = version;
        //加载公告
        if (updateJson.getPost().isEmpty()) {
            contents.add(new Content("公告", updateJson.getTime(), "公告加载失败，请检查网络连接！", 1));
        } else {
            contents.add(new Content("公告", updateJson.getTime(), updateJson.getPost(), 1));
        }
        //检测更新
        if (updateJson.getV() > v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("发现更新，是否下载更新？")
                            .setMessage("版本：" + updateJson.getV1() + "\n" + "更新内容：" + updateJson.getContent() + "\n" + "更新时间：" + updateJson.getTime())
                            .setPositiveButton("下载更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri uri = Uri.parse(updateJson.getUrl());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                    Looper.loop();
                }
            }).start();
        } else {
            Log.d("sss123", "暂无更新");
        }
    }

    //TODO 获取网络上的图片 banner
    class GetNetPic extends AsyncTask<String, Integer, Bitmap> {
        String url;
        Bitmap bitmap;

        public GetNetPic(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder().url(url).get().build();
            try {
                response = client.newCall(request).execute();
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmaps.add(bitmap);
            if (bitmaps.size() > 2) {
                bitmaps1.clear();
                bitmaps1.add(bitmaps.get(bitmaps.size() - 1));
                for (int i = 0; i < bitmaps.size(); i++) {
                    bitmaps1.add(bitmaps.get(i));
                }
                bitmaps1.add(bitmaps.get(0));
                viewPager.setAdapter(viewPagerAdapter);
                index = 1;
                viewPager.setCurrentItem(index);
            }
        }
    }

    //TODO 解析ListViewItem的项目
    public void getlistJson() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("https://ampk119-1255693066.cos.ap-chongqing.myqcloud.com/AamiricePhone/listcontent.json").build();
        String data = "";
        try {
            data = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.optString("name");
                String time = jsonObject.optString("time");
                String content = jsonObject.optString("content");
                int type = jsonObject.optInt("type");
                String bitmap = jsonObject.optString("bitmap");
                if (bitmap.isEmpty() || bitmap.length() < 10) {
                    contents.add(new Content(name, time, content, type));
                    handler.sendEmptyMessage(1);
                } else {
                    new GetListItem(bitmap, name, time, content, type).doInBackground();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //TODO 解析ListViewItem项目内容
    class GetListItem extends AsyncTask<String, Void, Content> {
        String bitmapurl;
        String name, time, content;
        int type;
        Bitmap bitmap;
        Content content1;

        public GetListItem(String bitmapurl, String name, String time, String content, int type) {
            this.bitmapurl = bitmapurl;
            this.name = name;
            this.time = time;
            this.content = content;
            this.type = type;
        }

        @Override
        protected Content doInBackground(String... strings) {
            if (bitmapurl.isEmpty() || bitmapurl.length() < 10) {
            } else {
                OkHttpClient client = new OkHttpClient.Builder().build();
                Request request = new Request.Builder().url(bitmapurl).build();
                try {
                    bitmap = BitmapFactory.decodeStream(client.newCall(request).execute().body().byteStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content1 = new Content(name, time, content, type, bitmap);
                contents.add(content1);
                handler.sendEmptyMessage(1);
            }
            return null;
        }
    }

    //TODO ViewPager改变
    class ViewPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            index = position;
            if (index == 0) {
                index = bitmaps1.size() - 2;
            } else if (index == bitmaps1.size() - 1) {
                index = 1;
            }
            if (position != index) {
                viewPager.setCurrentItem(index, true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    //TODO 开始时启动周期线程
    @Override
    public void onStart() {
        super.onStart();
        //判断公告是否有内容，判断banner是否有内容，没有则请求网络数据
        if (contents.isEmpty()) {
            getData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getlistJson();
                }
            }).start();
        } else {
        }
        listView_search.setVisibility(View.GONE);
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (bitmaps.size() >= 3) {
                    index++;
                    handler.sendEmptyMessage(2);
                }
            }
        }, 3, 3, TimeUnit.SECONDS);
    }

    //TODO 停止周期线程
    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) scheduledExecutorService.shutdown();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pager_home, null);
            initView();
            lvContentAdapter = new LVContentAdapter(getContext(), R.layout.contentlist, contents);
            viewPagerAdapter = new ViewPagerAdapter(getContext(), bitmaps1, bitmapslink);
            listView_content.setAdapter(lvContentAdapter);
            viewPager.setAdapter(viewPagerAdapter);
        } else {
            listView_content.setAdapter(lvContentAdapter);
            viewPager.setAdapter(viewPagerAdapter);
        }
    }
}
