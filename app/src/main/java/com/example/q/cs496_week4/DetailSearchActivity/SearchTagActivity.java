package com.example.q.cs496_week4.DetailSearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchTagActivity extends AppCompatActivity {

    TextView mSearch;
    TextView mKeyWord;
    TextView mCategory;
    TextView mIngredient;
    TextView mCreater;
    TextView mUpdated;
    Retrofit retrofit;
    HttpInterface httpInterface;
    ArrayList<String> mKeywords;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tag);

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);
//
        mSearch = (TextView) findViewById(R.id.textView_tag);

        Bundle bundle = getIntent().getExtras();

        ArrayList<String> keywords = bundle.getStringArrayList("keyword");
        bundle.remove("keyword");

//
//        Call<JsonObject> getSearchTag = httpInterface.getSearchTag(URLEncoder.encode(keyword));
//        getSearchTag.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                try {
//                    JsonArray tags = response.body().get("result").getAsJsonArray();
//                    for(int j=0;j<tags.size();j++){
//                        mKeywords.add(tags.get(j).getAsJsonObject().get("keyword").getAsString());
//                    }
//                } catch (Exception e) {
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
//
//            }
//        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_tag);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TagRecyclerAdapter(keywords, this);
        mRecyclerView.setAdapter(mAdapter);
        //setRecipeAdpater(this, this);

        final Button search_but = (Button) findViewById(R.id.search_buttag);

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(mSearch.getText().toString()));

                getPageCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JsonObject object = response.body().get("content").getAsJsonObject();
                            JsonArray recipes = response.body().get("recipes").getAsJsonArray();
                            JsonArray tags = response.body().get("tags").getAsJsonArray();
                            if (object != null) {
                                Log.d("<<<<", tags.toString());
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
                                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                                i.putExtra("keyword", keyword);
                                i.putExtra("ingredient", ingredient);
                                i.putExtra("category", category);
                                i.putExtra("category2", category2);
                                i.putExtra("tags", tags_got);
                                i.putExtra("creater", creater);
                                i.putExtra("updated_at", updated_at);
                                i.putExtra("recipes", got_recipe);
                                startActivity(i);

                            }
                        }catch(Exception e){
                            Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
                            i.putExtra("keyword", mSearch.getText().toString());
                            startActivity(i);
                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }


}
