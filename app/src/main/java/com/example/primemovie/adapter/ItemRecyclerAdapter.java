package com.example.primemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.primemovie.MoviesDetails;
import com.example.primemovie.R;
import com.example.primemovie.model.CategoryItemList;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
    Context context;
    List<CategoryItemList> categoryItemList;

    public ItemRecyclerAdapter(Context context, List<CategoryItemList> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ItemViewHolder holder, final int position) {
    //glide to fetch image from server
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MoviesDetails.class);
                i.putExtra("movieId",categoryItemList.get(position).getId());
                i.putExtra("movieName",categoryItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl",categoryItemList.get(position).getImageUrl());
                i.putExtra("movieFile",categoryItemList.get(position).getFileUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    public static final class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView itemImage;

        public ItemViewHolder(@NonNull View itemView) {

            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}
