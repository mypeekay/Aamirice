package com.peekay.aamirice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.SettingBean;

import java.util.List;

public class SettingAdapter extends ArrayAdapter<SettingBean> {
    int i;

    public SettingAdapter(Context context, int resource, List<SettingBean> list) {
        super(context, resource, list);
        i = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        SettingBean setting = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(i, null);
            viewHolder = new ViewHolder();
            viewHolder.textView_name = view.findViewById(R.id.tv_setting_name);
            viewHolder.textView_intro = view.findViewById(R.id.tv_setting_intro);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView_name.setText(setting.getName());
        viewHolder.textView_intro.setText(setting.getIntro());
        return view;
    }

    class ViewHolder {
        TextView textView_name, textView_intro;
    }
}
