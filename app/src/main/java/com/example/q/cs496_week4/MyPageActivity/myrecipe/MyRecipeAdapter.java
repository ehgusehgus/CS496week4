package com.example.q.cs496_week4.MyPageActivity.myrecipe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.cs496_week4.R;

import java.util.ArrayList;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyViewHolder> {
    private ArrayList<MyRecipeItem> data;

    public MyRecipeAdapter(ArrayList<MyRecipeItem> data){
        this.data = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView nickname;
        public TextView category;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            nickname = itemView.findViewById(R.id.nickname);
            category = itemView.findViewById(R.id.category);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myrecipe,parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyRecipeItem item = data.get(position);
        holder.category.setText(item.getCategory());
        holder.nickname.setText(item.getNickname());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }




}

