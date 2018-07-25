package com.example.q.cs496_week4.DetailSearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TagRecyclerAdapter extends RecyclerView.Adapter<TagRecyclerAdapter.ViewHolder> {

    private ArrayList<String> arrayListOfRecipe;
    private Context mContext;

    public TagRecyclerAdapter(ArrayList<String> arrayListOfRecipe, Context context){
        this.arrayListOfRecipe = arrayListOfRecipe;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public ImageView mImageView;
        public ImageView mIndex;
        public TextView mContent;


        public ViewHolder(View v) {
            super(v);
            mIndex  = (ImageView) v.findViewById(R.id.itemImage);
            mContent = (TextView) v.findViewById(R.id.tvTitle);

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_single_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final int position2 = position;

        try {
            String content = arrayListOfRecipe.get(position);
            holder.mContent.setText(arrayListOfRecipe.get(position));
            Glide.with(mContext)
                    .load(HttpInterface.BaseURL+"images/"+arrayListOfRecipe.get(position)+".jpg")
                    .asBitmap()
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)        //Error상황에서 보여진다.
                    .into(holder.mIndex);
            holder.mIndex.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    Retrofit retrofit;
                    HttpInterface httpInterface;

                    retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(HttpInterface.BaseURL)
                            .build();
                    httpInterface = retrofit.create(HttpInterface.class);
                    Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(arrayListOfRecipe.get(position2)));
                    Log.d("!!!!",arrayListOfRecipe.get(position2) );
                    getPageCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            try {
                                Log.d("!!!!", response.body().toString());
                                JsonObject object = response.body().get("content").getAsJsonObject();
                                JsonArray recipes = response.body().get("recipes").getAsJsonArray();
                                JsonArray tags = response.body().get("tags").getAsJsonArray();

                                if (object != null) {
                                    String keyword = object.get("keyword").getAsString();
                                    String ingredient = object.get("ingredient").getAsString();
                                    String category = object.get("category_con").getAsString();
                                    String category2 = object.get("category_cooking").getAsString();
                                    String creater = object.get("creater").getAsString();
                                    String updated_at = object.get("updated_at").getAsString();
                                    ArrayList<String> got_recipe = new ArrayList<String>();
                                    for(int j=0;j<recipes.size();j++){
                                        got_recipe.add(recipes.get(j).getAsJsonObject().get("descript").getAsString());
                                    }
                                    ArrayList<String> tags_got = new ArrayList<String>();
                                    for(int j=0;j<tags.size();j++){
                                        tags_got.add(tags.get(j).getAsJsonObject().get("tag").getAsString());
                                    }
                                    Intent i = new Intent(mContext.getApplicationContext(), SearchActivity.class);
                                    i.putExtra("keyword", keyword);
                                    i.putExtra("ingredient", ingredient);
                                    i.putExtra("category", category);
                                    i.putExtra("category2", category2);
                                    i.putExtra("tags", tags_got);
                                    i.putExtra("creater", creater);
                                    i.putExtra("updated_at", updated_at);
                                    i.putExtra("recipes", got_recipe);

                                    ((Activity) mContext).startActivity(i);
                                }
                            }catch(Exception e){
                                Intent i = new Intent(mContext.getApplicationContext(), EmptySearchActivity.class);
                                i.putExtra("keyword",arrayListOfRecipe.get(position2) );
                                ((Activity) mContext).startActivity(i);

                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(mContext.getApplicationContext(), "FAILURE", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

        } catch (Exception e) {
            Log.d("onviewholder", "jsonexception");
        }

    }
    @Override
    public int getItemCount() {
        return arrayListOfRecipe.size();
    }
}
