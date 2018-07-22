package com.example.q.cs496_week4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<String> arrayListOfRecipe;
    private Context mContext;

    public RecipeAdapter(ArrayList<String> arrayListOfRecipe, Context context){
        this.arrayListOfRecipe = arrayListOfRecipe;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public ImageView mImageView;
        public TextView mIndex;
        public TextView mContent;


        public ViewHolder(View v) {
            super(v);

            mIndex  = (TextView) v.findViewById(R.id.index);
            mContent = (TextView) v.findViewById(R.id.content);

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.reciepe_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
             String content = arrayListOfRecipe.get(position);
             holder.mContent.setText(content);
             holder.mIndex.setText((position+1)+". ");

        } catch (Exception e) {
            Log.d("onviewholder", "jsonexception");
        }

    }
    @Override
    public int getItemCount() {
        return arrayListOfRecipe.size();
    }

}
