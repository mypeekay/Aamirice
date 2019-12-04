package com.peekay.aamirice.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.peekay.aamirice.R;
import com.peekay.aamirice.VideoPlayActivity;
import com.peekay.aamirice.adapter.PageAdapter;
import com.peekay.aamirice.adapter.PopAdapter;
import com.peekay.aamirice.bean.PopBean;

import java.util.ArrayList;
import java.util.List;

import tools.MyOpenHelper;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    PageAdapter pageAdapter;
    TextView textView_menu;
    ListPopupWindow popupWindow;
    Fragment fragment;
    List<PopBean> list = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();//movie名字
    private ArrayList<String> name = new ArrayList<>();//title
    PopAdapter adapter;
    View view;
    //是否可见
    public boolean isVisible = false;
    //是否初始化完成
    public boolean isInit = false;
    //是否已经加载过
    public boolean isLoadOver = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isInit = isVisibleToUser;
        setParam();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_home_fragment, null);
            initView();
            isInit = true;
            setParam();
            viewPager.setCurrentItem(0);
        }
        return view;
    }

    private void setParam() {
        if (isInit && isLoadOver && isVisible) {
            isLoadOver = true;
        }
    }

    //TODO 绑定
    public void initView() {
        fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        viewPager = view.findViewById(R.id.viewpager);
        if (name.isEmpty()) {
            name.add("首页公告—搜索电影");
            name.add("音乐播放");
//            name.add("歌舞");
            name.add("真相访谈");
//            name.add("风暴来临");
//            name.add("资源下载");
            name.add("米叔图集");
            name.add("推荐—父与子");
            name.add("软件设置");
            fragments.add(new PagerHome());
            fragments.add(new PageMusic());
//            fragments.add(new BlackNull());
            fragments.add(new PagerSatyamev());
//            fragments.add(new BlackNull());
//            fragments.add(new BlackNull());
            fragments.add(new PicCollectActivity());
            fragments.add(new FatherAndSon());
            fragments.add(new PageSetting());
        }
        pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), fragments, name);
        pageAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(6);
        tabLayout = view.findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);
        popupWindow = new ListPopupWindow(getActivity());
        list.add(new PopBean("刷新白屏"));
        list.add(new PopBean("重载页面"));
        list.add(new PopBean("选择主题"));
        list.add(new PopBean("反馈吐个槽"));
        list.add(new PopBean("刷新数据库"));
        list.add(new PopBean("关于软件"));
        popupWindow.setWidth(400);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        textView_menu = view.findViewById(R.id.tv_menu);
        adapter = new PopAdapter(getContext(), R.layout.pop_item, list);
        popupWindow.setAdapter(adapter);
        popupWindow.setAnchorView(textView_menu);
//        popupWindow.setModal(false);
//        popupWindow.setDropDownGravity(Gravity.CENTER);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choose = list.get(position).getTitle();
                String s = "未完成的功能";
                switch (choose) {
                    case "刷新白屏":
                        pageAdapter.notifyDataSetChanged();
                        s = "刷新白屏成功！";
                        break;
                    case "重载页面":
                        getActivity().recreate();
                        s = "重载页面成功！";
                        break;
                    case "选择主题":
                        NavHostFragment.findNavController(fragment).navigate(R.id.action_homeFragment_to_selectTheme);
                        s = "选择主题";
                        break;
                    case "关于软件":
                        NavHostFragment.findNavController(fragment).navigate(R.id.action_homeFragment_to_aboutFragment);
                        s = "关于软件";
                        break;
                    case "反馈吐个槽":
                        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                        intent.putExtra("uri", "https://support.qq.com/products/97277");
                        startActivity(intent);
                        s = "反馈吐个槽";
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
                        s = "刷新数据库成功!";
                        break;
                    case "":
                        break;
                    case " ":
                        break;
                    case "  ":
                        break;
                    case "   ":
                        break;
                    default:
                        s = "未完成的功能！";
                        break;
                }
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
        textView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show();
            }
        });
    }
}
