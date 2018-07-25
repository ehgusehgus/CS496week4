package com.example.q.cs496_week4.DetailSearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_week4.DetailEditActivity.EditActivity;
import com.example.q.cs496_week4.DetailEditActivity.Model;
import com.example.q.cs496_week4.DetailEditActivity.MultiViewTypeAdapter;

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
    ArrayList<String> mKeywords = new ArrayList<>();

    ArrayList<String> mRecipes = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        accessToken = AccessToken.getCurrentAccessToken();
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        final String keyword = extras.getString("keyword");
        final String ingredient = extras.getString("ingredient");
        final String category = extras.getString("category");
        final String category_got2 = extras.getString("category2");
        final String creater = extras.getString("creater");
        final String updated = extras.getString("updated_at");
        final ArrayList<String> recipes = extras.getStringArrayList("recipes");
        final ArrayList<String> tag = extras.getStringArrayList("tags");
//        final ArrayList<String> recipes_image_prev = extras.getStringArrayList("recipes_image");
//        final ArrayList<Bitmap> recipes_image_post = new ArrayList<Bitmap>();

//        byte[] decodedString = Base64.decode(bitmap, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        for(int j=0; j<recipes_image_prev.size();j++){
//            byte[] decodedString2 = Base64.decode(recipes_image_prev.get(j),Base64.DEFAULT);
//            recipes_image_post.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
//        }
        String tags="";
        for(int k=0; k<tag.size()-1;k++){
            tags= tags + tag.get(k).toString()+",";
        }
        if(tag.size()>=1)
            tags = tags +tag.get(tag.size()-1);

        ArrayList<Model2> list= new ArrayList();
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"요리이름",keyword,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"나라별요리",category,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"조리방법",category_got2,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"재료",ingredient,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"달러태그",tags,recipes,null));
        list.add(new Model2(Model2.SEARCH_IMAGE_TYPE,"대표사진",keyword,recipes, null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"레시피","",recipes,null));
        for(int j=0;j<recipes.size();j++){
            list.add(new Model2(Model2.SEARCH_RECIPE_TYPE,(j+1)+"",recipes.get(j),recipes,keyword));
        }

        MultiViewTypeAdapter2 adapter = new MultiViewTypeAdapter2(list,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

//
//
//        mKeyWord = (TextView) findViewById(R.id.textView4);
//        mKeyWord.setText(keyword);
//        mCategory= (TextView) findViewById(R.id.textView5);
//        mCategory.setText(category);
//        mIngredient = (TextView) findViewById(R.id.textView6);
//        mIngredient.setText(ingredient);
//        mCreater = (TextView) findViewById(R.id.textView8);
//        mCreater.setText(creater);
//        mUpdated = (TextView) findViewById(R.id.textView9);
//        mUpdated.setText(updated);
//        mRecipes =recipes;
//
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewsearch);
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        setRecipeAdpater(this, this);
//
//
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);
//
        mSearch = (TextView) findViewById(R.id.textVie);
        ImageButton edit_but = (ImageButton) findViewById(R.id.edit_but2);

        String[] words = tags.split(",");
        String tags_input= "";
        for(int k=0; k<words.length-1;k++){
            tags_input =tags_input+"$"+words[k]+" ";
        }
        if(words.length>=1)
            tags_input = tags_input+"$"+words[words.length-1];

        Log.d("words", words.toString());
        final String tags_input2 = tags_input;

        edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("keyword",keyword);
                i.putExtra("ingredient",ingredient);
                i.putExtra("category",category);
                i.putExtra("category2", category_got2);
                i.putExtra("tag",tags_input2);
                i.putExtra("creater",creater);
                i.putExtra("updated",updated);
                i.putExtra("recipes",recipes);
                startActivity(i);
            }
        });

        final ImageButton search_but = (ImageButton) findViewById(R.id.search_but2);

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<JsonObject> getPageCall = httpInterface.getSearchTag(URLEncoder.encode(mSearch.getText().toString()));

                getPageCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            JsonObject object = response.body().get("result").getAsJsonObject();
                            JsonArray tags = object.get("result").getAsJsonArray();
                            if (tags.size() == 0) {
                                Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
                                i.putExtra("keyword", mSearch.getText().toString());
                                startActivity(i);
                                finish();
                            } else {
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

        final Button interest_but = (Button) findViewById(R.id.interest);

        Call<JsonObject> isInterest = httpInterface.isInterest(accessToken.getUserId(), URLEncoder.encode(keyword));
        isInterest.enqueue(new Callback<JsonObject>() {
                               @Override
                               public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                   try {
                                       if(response.body().get("result").getAsBoolean())
                                           interest_but.setText("★");
                                   } catch (Exception e) {
                                   }
                               }
                               @Override
                               public void onFailure(Call<JsonObject> call, Throwable t) {
                                   Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
                               }
                           });


        interest_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interest_but.getText().toString().equals("☆")) {
                    interest_but.setText("★");
                    Call<JsonObject> onInterest = httpInterface.onInterest(accessToken.getUserId(), keyword);
                    onInterest.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
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
                else {
                    interest_but.setText("☆");
                    Call<JsonObject> offInterest = httpInterface.offInterest(accessToken.getUserId(), keyword);
                    offInterest.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
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
        });

    }
}

