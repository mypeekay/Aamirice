package com.peekay.aamirice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.LeftList;

import java.util.List;

public class LeftListAdapter extends ArrayAdapter<LeftList> {
    int i;

    public LeftListAdapter(Context context, int resource, List<LeftList> leftLists) {
        super(context, resource, leftLists);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        LeftList leftList = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.img_music);
            viewHolder.textView_name = view.findViewById(R.id.tv_music_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_name.setText(leftList.getName());
        viewHolder.imageView.setImageResource(leftList.getPic());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView_name;
    }
}
