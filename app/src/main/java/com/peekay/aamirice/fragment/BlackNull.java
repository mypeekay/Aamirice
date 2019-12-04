package com.peekay.aamirice.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peekay.aamirice.R;

public class BlackNull extends Fragment {
    View view;
    Button button;
    int i = 0;
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
            view = inflater.inflate(R.layout.blacknull, null);
            button = view.findViewById(R.id.btn_null);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    button.setText("加油，你已经点了"+i+"次了");
                }
            });
            isInit = true;
            setParam();
        }
        return view;
    }

    private void setParam() {
        if (isInit && isLoadOver && isVisible) {
            isLoadOver = true;
        }
    }
}
