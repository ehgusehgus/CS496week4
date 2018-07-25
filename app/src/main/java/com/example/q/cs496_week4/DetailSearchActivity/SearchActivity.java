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
        if(tag.size()>1)
            tags = tags +tag.get(tag.size()-1);

        ArrayList<Model2> list= new ArrayList();
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"KEYWORD",keyword,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"CATEGORY_COUNTRY",category,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"CATEGORY_COOKING",category_got2,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"INGREDIENT",ingredient,recipes,null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"TAG",tags,recipes,null));
        list.add(new Model2(Model2.SEARCH_IMAGE_TYPE,"REPRESENTATIVE IMAGE",keyword,recipes, null));
        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"RECIPE","",recipes,null));
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
        Button edit_but = (Button) findViewById(R.id.edit_but2);

        String[] words = tags.split(",");
        String tags_input= "";
        for(int k=0; k<words.length-1;k++){
            tags_input =tags_input+"$"+words[k]+" ";
        }
        if(words.length>1)
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
                            JsonArray tags = response.body().get("tags").getAsJsonArray();
                            if (object != null) {

                                String keyword = object.get("keyword").getAsString();
                                String ingredient = object.get("ingredient").getAsString();
                                String category = object.get("category_con").getAsString();
                                String category2 = object.get("category_cooking").getAsString();
                                String creater = object.get("creater").getAsString();
                                String tag = object.get("tag").getAsString();
                                String updated_at = object.get("updated_at").getAsString();
                                ArrayList<String> got_recipe = new ArrayList<String>();
                                for(int j=0;j<recipes.size();j++){
                                    got_recipe.add(recipes.get(j).getAsJsonObject().get("descript").getAsString());
                                }
                                ArrayList<String> tags_got = new ArrayList<String>();
                                for(int j=0;j<tags.size();j++){
                                    tags_got.add(tags.get(j).getAsJsonObject().get("tag").getAsString());
                                }
                                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                                i.putExtra("keyword", keyword);
                                i.putExtra("ingredient", ingredient);
                                i.putExtra("category", category);
                                i.putExtra("category2", category2);
                                i.putExtra("tags", tags_got);
                                i.putExtra("creater", creater);
                                i.putExtra("updated_at", updated_at);
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

        final Button interest_but = (Button) findViewById(R.id.interest);

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

