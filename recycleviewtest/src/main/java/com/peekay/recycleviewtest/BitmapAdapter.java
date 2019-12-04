package com.peekay.recycleviewtest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class BitmapAdapter extends RecyclerView.Adapter<BitmapAdapter.ViewHolder> {

    List<Bitmaps> bitmaps;

    public BitmapAdapter(List<Bitmaps> bitmaps) {
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmaps bitmap = bitmaps.get(position);
        holder.imageView.setImageResource(bitmap.getA());
//        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
//        params.height = params.height + new Random().nextInt(300);
//        holder.imageView.setLayoutParams(params);
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
}
