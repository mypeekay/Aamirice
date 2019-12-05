package com.peekay.aamirice.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.peekay.aamirice.R;
import com.peekay.aamirice.adapter.AmStudioBitmapAdapter;
import com.peekay.aamirice.bean.AmStudioBitmapsLink;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmStudio extends Fragment {
    View view;
    SmartRefreshLayout smartRefreshLayout;
    AmStudioBitmapAdapter adapter;
    List<AmStudioBitmapsLink> uris = new ArrayList<>();
    RecyclerView recyclerView;
    TextView textView_tip;
    String res;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 2:
                    textView_tip.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        for (int j = 0; j < jsonArray.length(); j++) {
                            uris.add(new AmStudioBitmapsLink(jsonArray.getString(j)));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.amstudio, null);
            initView();
        }
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycle_amstudio);
        smartRefreshLayout = view.findViewById(R.id.smart);
        textView_tip = view.findViewById(R.id.tv_tips);
        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (uris.isEmpty()) {
                    getUri();
                }
                smartRefreshLayout.finishRefresh(10000);
            }
        });
        adapter = new AmStudioBitmapAdapter(getActivity(), getContext(), uris, R.layout.recycleitem_amstudio);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //        SetTheme setTheme = new SetTheme(swipeRefreshLayout);
//        sharedPreferences = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
//        setTheme.initSwipe(sharedPreferences.getString("theme", "null"));
    }

    public void getUri() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    res = OkHttpUtils.get().url("https://ampk119-1255693066.cos.ap-chongqing.myqcloud.com/AamiricePhone/%E5%9B%BE%E9%9B%86/amstudio.json")
                            .build().execute().body().string();
                    handler.sendEmptyMessage(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onSaveInstanceState(outState);
        }
    }
}