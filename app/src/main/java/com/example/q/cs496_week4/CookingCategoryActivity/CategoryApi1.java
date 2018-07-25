package com.example.q.cs496_week4.CookingCategoryActivity;

import com.example.q.cs496_week4.HttpInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryApi1 {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit1() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpInterface.BaseURL)
                    .build();
        }
        return retrofit;
    }
}
