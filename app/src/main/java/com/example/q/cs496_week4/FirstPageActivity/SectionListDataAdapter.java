package com.example.q.cs496_week4.FirstPageActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.q.cs496_week4.DetailSearchActivity.EmptySearchActivity;
import com.example.q.cs496_week4.DetailSearchActivity.SearchActivity;
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

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<CardItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter(Context context, ArrayList<CardItemModel> itemsList){
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SectionListDataAdapter.SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull SectionListDataAdapter.SingleItemRowHolder singleItemRowHolder, int i) {

        CardItemModel singleItem = itemsList.get(i);

        singleItemRowHolder.tvTitle.setText(singleItem.getKeyword());

        Glide.with(mContext)
                .load("http://52.231.67.203:3000/images/" + singleItem.getKeyword() + ".jpg")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(singleItemRowHolder.itemImage);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected ImageView itemImage;

        public SingleItemRowHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(HttpInterface.BaseURL)
                            .build();
                    HttpInterface httpInterface = retrofit.create(HttpInterface.class);

                    Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(tvTitle.getText().toString()));

                    getPageCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("??????", response.body().toString());
                            try {
                                JsonObject object = response.body().get("content").getAsJsonObject();
                                JsonArray recipes = response.body().get("recipes").getAsJsonArray();
                                JsonArray tags = response.body().get("tags").getAsJsonArray();

                                if (object != null) {

                                    String keyword = object.get("keyword").getAsString();
                                    String ingredient = object.get("ingredient").getAsString();
                                    String category = object.get("category_con").getAsString();
                                    String category2 = object.get("category_cooking").getAsString();
                                    ArrayList<String> tags_got = new ArrayList<String>();
                                    for(int j=0;j<tags.size();j++){
                                        tags_got.add(tags.get(j).getAsJsonObject().get("tag").getAsString());
                                    }
                                    String creater = object.get("creater").getAsString();
                                    String updated_at = object.get("updated_at").getAsString();
                                    ArrayList<String> got_recipe = new ArrayList<String>();
                                    for(int j=0;j<recipes.size();j++){
                                        got_recipe.add(recipes.get(j).getAsJsonObject().get("descript").getAsString());
                                    }

                                    Intent i = new Intent(mContext, SearchActivity.class);
                                    i.putExtra("keyword", keyword);
                                    i.putExtra("ingredient", ingredient);
                                    i.putExtra("category", category);
                                    i.putExtra("category2", category2);
                                    i.putExtra("tags", tags_got);
                                    i.putExtra("creater", creater);
                                    i.putExtra("updated_at", updated_at);
                                    i.putExtra("recipes", got_recipe);
                                    //Log.d("dddd","ddddd");
                                    ((Activity) mContext).startActivity(i);

                                }
                            }catch(Exception e){
                                Intent i = new Intent(mContext, EmptySearchActivity.class);
                                i.putExtra("keyword",tvTitle.getText());
                                ((Activity) mContext).startActivity(i);

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(mContext, "FAILURE", Toast.LENGTH_LONG).show();

                        }
                    });
                    Toast.makeText(view.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
