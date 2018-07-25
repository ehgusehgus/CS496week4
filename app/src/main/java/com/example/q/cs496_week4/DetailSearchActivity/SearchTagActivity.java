package com.example.q.cs496_week4.DetailSearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_week4.DetailEditActivity.EditActivity;
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
    ArrayList<String> mKeywords = new ArrayList<>();

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

        final ImageButton search_but = (ImageButton) findViewById(R.id.search_buttag);

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Call<JsonObject> getSearchTag = httpInterface.getSearchTag(URLEncoder.encode(mSearch.getText().toString()));
                        getSearchTag.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                try {
                                    JsonObject object = response.body().get("result").getAsJsonObject();
                                    JsonArray tags = object.get("result").getAsJsonArray();
                                    if(tags.size() ==0){
                                        Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
                                        i.putExtra("keyword", mSearch.getText().toString());
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        for (int j = 0; j < tags.size(); j++) {
                                            mKeywords.add(tags.get(j).getAsJsonObject().get("keyword").getAsString());
                                        }
                                        Intent i = new Intent(getApplicationContext(), SearchTagActivity.class);
                                        i.putExtra("keyword", mKeywords);
                                        startActivity(i);
                                        finish();
                                    }
                                } catch (Exception e) {
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
        });

        final ImageButton edit_but = (ImageButton) findViewById(R.id.edit_buttag);

        edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("keyword","");
                i.putExtra("ingredient","");
                i.putExtra("category","");
                i.putExtra("category2","");
                i.putExtra("tag","");
                i.putExtra("creater","");
                i.putExtra("updated","");
                i.putExtra("recipes",new ArrayList<String>());
                startActivity(i);
                finish();
            }
        });

    }


}
