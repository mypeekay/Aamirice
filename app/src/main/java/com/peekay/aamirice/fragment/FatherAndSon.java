package com.peekay.aamirice.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.peekay.aamirice.PicActivity;
import com.peekay.aamirice.R;
import com.peekay.aamirice.VideoPlayActivity;
import com.peekay.aamirice.bean.BitmapSave;

public class FatherAndSon extends Fragment implements View.OnClickListener {
    View view;
    Button button_father1, button_father2, button_father3, button_father4, button_father5;
    ImageView imageView_father;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_father_and_song, null);
        initView();
        return view;
    }

    public void initView() {
        button_father1 = view.findViewById(R.id.btn_father1);
        button_father2 = view.findViewById(R.id.btn_father2);
        button_father3 = view.findViewById(R.id.btn_father3);
        button_father4 = view.findViewById(R.id.btn_father4);
        button_father5 = view.findViewById(R.id.btn_father5);
        button_father1.setOnClickListener(this);
        button_father2.setOnClickListener(this);
        button_father3.setOnClickListener(this);
        button_father4.setOnClickListener(this);
        button_father5.setOnClickListener(this);
        imageView_father = view.findViewById(R.id.img_father);
        imageView_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), PicActivity.class));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.father);
                BitmapSave.setBitmap(bitmap);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
        switch (v.getId()) {
            case R.id.btn_father1:
                intent.putExtra("uri", "https://www.nfmovies.com/video/?15435-0-0.html");
                break;
            case R.id.btn_father2:
                intent.putExtra("uri", "https://www.nfmovies.com/video/?15435-0-1.html");
                break;
            case R.id.btn_father3:
                intent.putExtra("uri", "https://www.nfmovies.com/video/?15435-0-2.html");
                break;
            case R.id.btn_father4:
                intent.putExtra("uri", "https://www.nfmovies.com/video/?15435-0-3.html");
                break;
            case R.id.btn_father5:
                intent.putExtra("uri", "https://www.nfmovies.com/video/?15435-0-4.html");
                break;
        }
        startActivity(intent);
    }
}
