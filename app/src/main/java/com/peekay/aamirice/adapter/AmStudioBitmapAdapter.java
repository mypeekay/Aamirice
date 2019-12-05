package com.peekay.aamirice.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.peekay.aamirice.PicActivity;
import com.peekay.aamirice.R;
import com.peekay.aamirice.bean.AmStudioBitmapsLink;
import com.peekay.aamirice.bean.BitmapSave;
import java.util.List;

public class AmStudioBitmapAdapter extends RecyclerView.Adapter<AmStudioBitmapAdapter.ViewHolder> {
    Activity activity;
    Context context;
    List<AmStudioBitmapsLink> bitmaps;
    int i;

    public AmStudioBitmapAdapter(Activity activity, Context context, List<AmStudioBitmapsLink> bitmaps, int i) {
        this.bitmaps = bitmaps;
        this.context = context;
        this.activity = activity;
        this.i = i;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(i, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AmStudioBitmapsLink amStudioBitmaps = bitmaps.get(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PicActivity.class));
                BitmapDrawable bd = (BitmapDrawable) holder.imageView.getDrawable();
                BitmapSave.setBitmap(bd.getBitmap());
            }
        });
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context).load(amStudioBitmaps.getUri()).into(holder.imageView);
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
    }
}
