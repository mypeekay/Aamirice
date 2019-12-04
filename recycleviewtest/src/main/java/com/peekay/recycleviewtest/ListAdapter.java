package com.peekay.recycleviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Bitmaps> {
    int i;

    public ListAdapter(Context context, int resource, List<Bitmaps> bitmaps) {
        super(context, resource, bitmaps);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        Bitmaps bitmaps = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.image_lv);
            viewHolder.imageView1 = view.findViewById(R.id.image1_lv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position % 2 == 0) {
            viewHolder.imageView.setImageResource(bitmaps.getA());
        }else {
            viewHolder.imageView1.setImageResource(bitmaps.getA());
        }
        return view;
    }

    class ViewHolder {
        ImageView imageView, imageView1;
    }
}
