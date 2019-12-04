package com.peekay.aamirice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.Theme;

import java.util.List;

public class ThemeAdapter extends ArrayAdapter<Theme> {
    List<Theme> list;
    int i;

    public ThemeAdapter(Context context, int resource, List<Theme> list) {
        super(context, resource,list);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        Theme theme = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_name = view.findViewById(R.id.tv_name);
            viewHolder.textView_color = view.findViewById(R.id.tv_color);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_name.setText(theme.getName());
        viewHolder.textView_name.setTextColor(theme.getColor());
        viewHolder.textView_color.setBackgroundColor(theme.getColor());
        return view;
    }

    class ViewHolder {
        TextView textView_name, textView_color;
    }
}
