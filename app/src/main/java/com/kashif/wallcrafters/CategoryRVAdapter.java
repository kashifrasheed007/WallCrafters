package com.kashif.wallcrafters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryRVModel> categoryRVModelsArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface; // this is initialize at the end

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModelsArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelsArrayList = categoryRVModelsArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_items, parent, false);
        CategoryRVAdapter.ViewHolder holder = new CategoryRVAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryRVModel categoryRVModel = categoryRVModelsArrayList.get(position);
        holder.tvCat.setText(categoryRVModel.getCategory());
        Glide.with(context)
                .load(categoryRVModel.getCategotryImageUrl())
                .into(holder.catItemImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModelsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCat;
        private ImageView catItemImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCat = itemView.findViewById(R.id.tvCat);
            catItemImageView = itemView.findViewById(R.id.catItemImageView);
        }
    }

    // make a interface class to adding a click listener
    public interface CategoryClickInterface{
       void onCategoryClick(int position);
    }
}
