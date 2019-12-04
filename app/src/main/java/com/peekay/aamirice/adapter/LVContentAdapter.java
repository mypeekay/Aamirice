package com.peekay.aamirice.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.peekay.aamirice.PicActivity;
import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.BitmapSave;
import com.peekay.aamirice.bean.Content;

import java.util.List;

public class LVContentAdapter extends ArrayAdapter<Content> {
    int i;

    public LVContentAdapter(Context context, int resource, List<Content> contents) {
        super(context, resource, contents);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        final Content content = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_name = view.findViewById(R.id.tv_name);
            viewHolder.textView_time = view.findViewById(R.id.tv_time);
            viewHolder.textView_content = view.findViewById(R.id.tv_content);
            viewHolder.imageView_ava = view.findViewById(R.id.img_ava);
            viewHolder.imageView1 = view.findViewById(R.id.img1);
            viewHolder.imageView_vip = view.findViewById(R.id.img_vip);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_name.setText(content.getName());
        viewHolder.textView_time.setText("发布时间：" + content.getTime());
        viewHolder.textView_content.setText(content.getContent());
        viewHolder.imageView1.setImageBitmap(content.getBitmap());
        viewHolder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(),PicActivity.class));
                BitmapSave.setBitmap(content.getBitmap());
            }
        });
        switch (content.getType()) {
            case 1:
                viewHolder.imageView_ava.setImageResource(R.drawable.u17);
                viewHolder.imageView_vip.setVisibility(View.VISIBLE);
                viewHolder.textView_content.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                viewHolder.textView_content.setAutoLinkMask(Linkify.ALL);
                viewHolder.textView_content.setText(content.getContent());
                viewHolder.textView_content.setTextSize(16);
                break;
            default:
                viewHolder.imageView_ava.setImageResource(R.mipmap.icon);
                viewHolder.imageView_vip.setVisibility(View.GONE);
                viewHolder.textView_content.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                viewHolder.textView_content.setAutoLinkMask(0);
                viewHolder.textView_content.setText(content.getContent());
                viewHolder.textView_content.setTextSize(14);
                break;
        }
        return view;
    }

    class ViewHolder {
        TextView textView_name, textView_time, textView_content;
        ImageView imageView_ava,imageView1,imageView_vip;
    }

}
