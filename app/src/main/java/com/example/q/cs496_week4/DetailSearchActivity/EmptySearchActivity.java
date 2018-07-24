package com.example.q.cs496_week4.DetailSearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_week4.DetailEditActivity.EditActivity;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.facebook.AccessToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmptySearchActivity extends AppCompatActivity {

    AccessToken accessToken;
    TextView mSearch;
    TextView mKeyword;
    Retrofit retrofit;
    HttpInterface httpInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_search);

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);

        mSearch = (TextView) findViewById(R.id.textView10);
        mKeyword =(TextView) findViewById(R.id.textView11);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        final String keyword = extras.getString("keyword");
        mKeyword.setText(keyword);

        Button edit_but = (Button) findViewById(R.id.edit_but3);

        edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("keyword",keyword);
                i.putExtra("ingredient","");
                i.putExtra("category","");
                i.putExtra("category2","");
                i.putExtra("tag","");
                i.putExtra("creater","");
                i.putExtra("updated","");
                i.putExtra("recipes",new ArrayList<String>());
                startActivity(i);
            }
        });

        final Button search_but = (Button) findViewById(R.id.search_but3);

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(mSearch.getText().toString()));

                getPageCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("??????", response.body().toString());
                        try {
                            JsonObject object = response.body().get("content").getAsJsonObject();
                            JsonArray recipes = response.body().get("recipes").getAsJsonArray();
                            if (object != null) {

                                String keyword = object.get("keyword").getAsString();
                                String ingredient = object.get("ingredient").getAsString();
                                String category = object.get("category_con").getAsString();
                                String category2 = object.get("category_cooking").getAsString();

                                String encodedImage = object.get("image").getAsString();

                                String creater = object.get("creater").getAsString();
                                String updated_at = object.get("updated_at").getAsString();
                                ArrayList<String> got_recipe = new ArrayList<String>();
                                for(int j=0;j<recipes.size();j++){
                                    got_recipe.add(recipes.get(j).getAsJsonObject().get("descript").getAsString());
                                }

                                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                                i.putExtra("keyword", keyword);
                                i.putExtra("ingredient", ingredient);
                                i.putExtra("category", category);
                                i.putExtra("category2", category2);
                                i.putExtra("creater", creater);
                                i.putExtra("updated_at", updated_at);
                                i.putExtra("image", encodedImage);
                                i.putExtra("recipes", got_recipe);
                                startActivity(i);
                                finish();

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



