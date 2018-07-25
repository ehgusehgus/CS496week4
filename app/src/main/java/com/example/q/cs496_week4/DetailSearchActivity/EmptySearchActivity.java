package com.example.q.cs496_week4.DetailSearchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    ArrayList<String> mKeywords = new ArrayList<>();

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

        ImageButton edit_but = (ImageButton) findViewById(R.id.edit_but3);

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
            }
        });

        final ImageButton search_but = (ImageButton) findViewById(R.id.search_but3);

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("?????", "");
                Call<JsonObject> getPageCall = httpInterface.getSearchTag(URLEncoder.encode(mSearch.getText().toString()));
                getPageCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("??????", response.body().toString());
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
}



