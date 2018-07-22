package com.example.q.cs496_week4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {



    AccessToken accessToken;
    TextView mSearch;
    TextView mKeyWord;
    TextView mCategory;
    TextView mIngredient;
    TextView mCreater;
    TextView mUpdated;
    Retrofit retrofit;
    HttpInterface httpInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String keyword = extras.getString("keyword");
        String ingredient = extras.getString("ingredient");
        String category = extras.getString("category");
        String creater = extras.getString("creater");
        String updated = extras.getString("updated_at");

        mKeyWord = (TextView) findViewById(R.id.textView4);
        mKeyWord.setText(keyword);
        mCategory= (TextView) findViewById(R.id.textView5);
        mCategory.setText(category);
        mIngredient = (TextView) findViewById(R.id.textView6);
        mIngredient.setText(ingredient);
        mCreater = (TextView) findViewById(R.id.textView8);
        mCreater.setText(creater);
        mUpdated = (TextView) findViewById(R.id.textView9);
        mUpdated.setText(updated);

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);

        mSearch = (TextView) findViewById(R.id.textVie);
        Button edit_but = (Button) findViewById(R.id.edit_but2);

        edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(i);
            }
        });

        final Button search_but = (Button) findViewById(R.id.search_but2);

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
                            if (object != null) {
                                String keyword = object.get("keyword").getAsString();
                                String ingredient = object.get("ingrediant").getAsString();
                                String category = object.get("category").getAsString();
                                String creater = object.get("creater").getAsString();
                                String updated_at = object.get("updated_at").getAsString();
                                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                                i.putExtra("keyword", keyword);
                                i.putExtra("ingredient", ingredient);
                                i.putExtra("category", category);
                                i.putExtra("creater", creater);
                                i.putExtra("updated_at", updated_at);
                                startActivity(i);
                                finish();
                            }
                        }catch(Exception e){
                            Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
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

