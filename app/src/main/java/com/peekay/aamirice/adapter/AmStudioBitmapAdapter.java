package com.peekay.aamirice.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peekay.aamirice.PicActivity;
import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.AmStudioBitmaps;
import com.peekay.aamirice.bean.BitmapSave;
import com.peekay.aamirice.fragment.AmStudio;

import java.util.List;

public class AmStudioBitmapAdapter extends RecyclerView.Adapter<AmStudioBitmapAdapter.ViewHolder> {

    Activity activity;
    Context context;
    List<AmStudioBitmaps> bitmaps;
    int i;

    public AmStudioBitmapAdapter(Activity activity, Context context, List<AmStudioBitmaps> bitmaps, int i) {
        this.bitmaps = bitmaps;
        this.context = context;
        this.activity = activity;
        this.i = i;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(i, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AmStudioBitmaps amStudioBitmaps = bitmaps.get(position);
        holder.imageView.setImageBitmap(amStudioBitmaps.getBitmap());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PicActivity.class));
                BitmapSave.setBitmap(bitmaps.get(position).getBitmap());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("sss123", "onViewDetachedFromWindow: 被调用");
    }
}
