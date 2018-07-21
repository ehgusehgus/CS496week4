package com.example.q.cs496_week4.UserActivity;

import android.content.Intent;
import android.util.Log;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.MyApplication;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Helper {

    public static void getUserAccount(String facebook_id){

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        HttpInterface httpInterface = retrofit.create(HttpInterface.class);
        Call<JsonObject> getUserCall = httpInterface.getUser(facebook_id);
        getUserCall.enqueue(new Callback<JsonObject>(){

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                if(object != null){
                    String res = object.get("nickname").toString();
                    MyApplication.setNickname(res);
                } else {

                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
