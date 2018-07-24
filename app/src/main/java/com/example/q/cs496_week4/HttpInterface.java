package com.example.q.cs496_week4;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HttpInterface {
    static final String BaseURL = "http://52.231.67.203:3000/";

    @FormUrlEncoded
    @POST("/users/create")
    Call<JsonObject> createUser(@Field("facebook_id") String facebook_id, @Field("nickname") String nickname);

    @GET("/users")
    Call<JsonObject> getUser(@Header("facebook_id") String facebook_id);

    @GET("/contents")
    Call<JsonObject> getPage(@Header("keyword") String keyword);

    @FormUrlEncoded
    @POST("/contents/create")
    Call<JsonObject> editPage(@Field("keyword") String keyword, @Field("ingredient") String ingredient, @Field("creater") String nickname, @Field("category_con") String category, @Field("category_cooking") String category2,@Field("tags") String tags, @Field("recipes") String recipes, @Field("image") String image);

    @GET("/")
    Call<JsonObject> getRandomAndLatestContent();

    @FormUrlEncoded
    @POST("/interest/on")
    Call<JsonObject> onInterest(@Field("facebook_id") String facebook_id, @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("/interest/off")
    Call<JsonObject> offInterest(@Field("facebook_id") String facebook_id, @Field("keyword") String keyword);

}
