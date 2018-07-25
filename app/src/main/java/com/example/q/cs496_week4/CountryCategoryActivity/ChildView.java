package com.example.q.cs496_week4.CountryCategoryActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.q.cs496_week4.CookingCategoryActivity.CategoryItem1;
import com.example.q.cs496_week4.DetailSearchActivity.EmptySearchActivity;
import com.example.q.cs496_week4.DetailSearchActivity.SearchActivity;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Layout(R.layout.category_child_layout)
public class ChildView {
    private static String TAG ="ChildView";

    @View(R.id.child_keyword)
    TextView textViewKeyword;

    @View(R.id.child_creater)
    TextView textViewCreater;

    @View(R.id.child_image)
    ImageView childImage;

    private Context mContext;
    private CategoryItem item;

    public ChildView(Context mContext, CategoryItem item) {
        this.mContext = mContext;
        this.item = item;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        textViewKeyword.setText(item.getKeyword());
        textViewKeyword.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Retrofit retrofit;
                HttpInterface httpInterface;

                retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(HttpInterface.BaseURL)
                        .build();
                httpInterface = retrofit.create(HttpInterface.class);
                Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(item.getKeyword()));
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
                            i.putExtra("keyword",item.getKeyword());
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

        Glide.with(mContext).load(HttpInterface.BaseURL+"images/"+item.getKeyword() +".jpg")
                .asBitmap()
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(childImage);

        //TODO: 사진 추가
    }
}
