package com.example.q.cs496_week4.CountryCategoryActivity;

import com.example.q.cs496_week4.HttpInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryApi {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpInterface.BaseURL)
                    .build();
        }
        return retrofit;
    }
}
