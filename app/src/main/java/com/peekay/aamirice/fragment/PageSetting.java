package com.peekay.aamirice.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.peekay.aamirice.R;
import com.peekay.aamirice.VideoPlayActivity;
import com.peekay.aamirice.adapter.SettingAdapter;
import com.peekay.aamirice.bean.SettingBean;

import java.util.ArrayList;

import tools.MyOpenHelper;
import tools.SetTheme;

public class PageSetting extends Fragment {
    View view;
    ListView listView;
    //是否可见
    public boolean isVisible = false;
    //是否初始化完成
    public boolean isInit = false;
    //是否已经加载过
    public boolean isLoadOver = false;
    ArrayList<SettingBean> lists = new ArrayList<>();
    SettingAdapter adapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isInit = isVisibleToUser;
        setParam();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.setting, null);
            isInit = true;
            setParam();
            listView = view.findViewById(R.id.lv_setting);
            if (lists.isEmpty()) {
                initLists();
            }
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = lists.get(position).getName();
                    switch (item) {
                        case "选择主题":
                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_selectTheme);
                            break;
                        case "刷新数据库":
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MyOpenHelper myOpenHelper = new MyOpenHelper(getContext());
                                    SQLiteDatabase sqLiteDatabase = myOpenHelper.getWritableDatabase();
                                    myOpenHelper.onUpgrade(sqLiteDatabase, 1, 3);
                                }
                            }).start();
                            Toast.makeText(getContext(), "刷新数据库成功！", Toast.LENGTH_LONG).show();
                            break;
                        case "反馈-吐个槽":
                            Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                            intent.putExtra("uri", "https://support.qq.com/products/97277");
                            startActivity(intent);
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse("https://support.qq.com/products/97277"));
//                            startActivity(intent);
                            break;
                        case "关于":
                            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_aboutFragment);
                            break;
                    }
                }
            });
        }
        return view;
    }

    private void setParam() {
        if (isInit && isLoadOver && isVisible) {
            isLoadOver = true;
        }
    }

    private void initLists() {
        lists.add(new SettingBean("选择主题", "选择一个你喜欢的主题颜色\n注意：切换主题建议重新打开软件！\n如遇白屏请点击右上角菜单的【刷新白屏】"));
        lists.add(new SettingBean("刷新数据库", "适用于升级版本后，搜索不到更新的资源内容时，重新创建数据库"));
        lists.add(new SettingBean("反馈-吐个槽", "呸呸呸，吐了个槽"));
        lists.add(new SettingBean("关于", "about Aamirice"));
        adapter = new SettingAdapter(getContext(), R.layout.setting_item, lists);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapter);
    }
}
