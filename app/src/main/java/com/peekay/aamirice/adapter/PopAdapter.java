package com.peekay.aamirice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.PopBean;

import java.util.List;

public class PopAdapter extends ArrayAdapter<PopBean> {
    int i;

    public PopAdapter(Context context, int resource, List<PopBean> beans) {
        super(context, resource, beans);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        PopBean popBean = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_title = view.findViewById(R.id.tv_pop_title);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_title.setText(popBean.getTitle());
        return view;
    }

    class ViewHolder {
        TextView textView_title;
    }
}
