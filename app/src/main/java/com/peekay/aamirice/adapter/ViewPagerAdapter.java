package com.peekay.aamirice.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;

import com.peekay.aamirice.R;
import com.peekay.aamirice.VideoPlayActivity;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Bitmap> datas;
    ArrayList<String> links;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Bitmap> datas, ArrayList<String> links) {
        this.context = context;
        this.datas = datas;
        this.links = links;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //渲染每一页的数据
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.viewpageritem, null);
        final ImageView imageView = view.findViewById(R.id.img);
        //设置显示的图片
        imageView.setImageBitmap(datas.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = links.get(position);
                if (uri.isEmpty() || uri.length() < 10) {
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    context.startActivity(intent);
                }
            }
        });
        //添加视图
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
