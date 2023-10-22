package com.kashif.wallcrafters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WallpaperRVAdapter extends RecyclerView.Adapter<WallpaperRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> wallpaperArrayList;

    public WallpaperRVAdapter(Context context, ArrayList<String> wallpaperArrayList) {
        this.context = context;
        this.wallpaperArrayList = wallpaperArrayList;
    }

    @NonNull
    @Override
    public WallpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_items, parent, false);
        WallpaperRVAdapter.ViewHolder holder = new WallpaperRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(wallpaperArrayList.get(position))
                .into(holder.wallItemImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperActivity.class);
                intent.putExtra("imageUrl", wallpaperArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView wallItemImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallItemImageView = (ImageView) itemView.findViewById(R.id.wallItemImageView);
        }
    }
}
