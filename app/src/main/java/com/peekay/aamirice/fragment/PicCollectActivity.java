package com.peekay.aamirice.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.peekay.aamirice.R;
import com.peekay.aamirice.adapter.PageAdapter;
import com.peekay.aamirice.fragment.BlackNull;

import java.util.ArrayList;

public class PicCollectActivity extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_pic_collect, null);
            tabLayout = view.findViewById(R.id.tab_piccollect);
            viewPager = view.findViewById(R.id.viewpager_piccollect);
            if (name.isEmpty()||fragments.isEmpty()) {
                name.clear();
                fragments.clear();
                name.add("AmStudio");
                name.add("偶素鱿鱼-合成图");
                name.add("半颗糖——拼图");
                name.add("表情包");
                name.add("其他图集");
                fragments.add(new AmStudio());
                fragments.add(new AmStudio1());
                fragments.add(new AmStudio2());
                fragments.add(new AmStudio3());
                fragments.add(new AmStudio4());
            }
            pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), fragments, name);
            pageAdapter.notifyDataSetChanged();
            viewPager.setAdapter(pageAdapter);
            tabLayout.setupWithViewPager(viewPager);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_pic_collect, null);
            tabLayout = view.findViewById(R.id.tab_piccollect);
            viewPager = view.findViewById(R.id.viewpager_piccollect);
            if (name.isEmpty()||fragments.isEmpty()) {
                name.clear();
                fragments.clear();
                name.add("AmStudio");
                name.add("偶素鱿鱼-合成图");
                name.add("半颗糖——拼图");
                name.add("表情包");
                name.add("其他图集");
                fragments.add(new AmStudio());
                fragments.add(new AmStudio1());
                fragments.add(new AmStudio2());
                fragments.add(new AmStudio3());
                fragments.add(new AmStudio4());
                pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), fragments, name);
                viewPager.setAdapter(pageAdapter);
                tabLayout.setupWithViewPager(viewPager);
            }
        }else {
        }
    }
}
