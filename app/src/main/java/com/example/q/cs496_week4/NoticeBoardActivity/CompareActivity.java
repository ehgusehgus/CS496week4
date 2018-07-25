package com.example.q.cs496_week4.NoticeBoardActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

public class CompareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);


        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        String keyword= getIntent().getExtras().getString("keyword");
        Log.d("TTTTTT", keyword);

        Call<JsonObject> getNoticeDetailCall = httpInterface.getNoticeDetail(URLEncoder.encode(keyword));
        getNoticeDetailCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("??????", response.body().toString());
                try {
                }catch(Exception e){
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
            }
        });



    }
}
