package com.example.q.cs496_week4;

import android.Manifest;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_week4.CategoryActivity.CategoryActivity;
import com.example.q.cs496_week4.DetailSearchActivity.EmptySearchActivity;
import com.example.q.cs496_week4.DetailSearchActivity.SearchTagActivity;
import com.example.q.cs496_week4.FirstPageActivity.FirstPage;
import com.example.q.cs496_week4.MyPageActivity.MyPageActivity;
import com.example.q.cs496_week4.NoticeBoardActivity.NoticeBoardActivity;
import com.example.q.cs496_week4.UserActivity.LoginActivity;
import com.example.q.cs496_week4.UserActivity.UserCreateActivity;
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

public class MainActivity extends TabActivity {


    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    AccessToken accessToken;
    TextView mSearch;
    Retrofit retrofit;
    HttpInterface httpInterface;
    ArrayList<String> mKeywords;
    public AccessToken getAccessToken() {
        return accessToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);

        mSearch = (TextView) findViewById(R.id.textView);
        //check login
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if(MyApplication.nickname.equals("")) {
            Call<JsonObject> getUserCall = httpInterface.getUser(accessToken.getUserId());
            getUserCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject object = response.body().get("result").getAsJsonObject();
                    if (object != null) {
                        if(object.get("nickname") == null){
                            Intent intent = new Intent(getApplication(),UserCreateActivity.class);
                            startActivity(intent);
                        } else {
                            String res = object.get("nickname").toString();
                            Toast.makeText(getApplication(), res, Toast.LENGTH_LONG).show();
                            MyApplication.setNickname(object.get("nickname").toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();

                }
            });
        }
        // Here, thisActivity is the current activity
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS,
                    0);
        } else {
            doOncreate();
        }

        doOncreate();


//        Button edit_but = (Button) findViewById(R.id.edit_but);
//
//        edit_but.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent i = new Intent(getApplicationContext(), EditActivity.class);
//            startActivity(i);
//        }
//    });

    final ImageButton search_but = (ImageButton) findViewById(R.id.search_but);

        search_but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mKeywords = new ArrayList<>();

            Call<JsonObject> getSearchTag = httpInterface.getSearchTag(URLEncoder.encode(mSearch.getText().toString()));
            getSearchTag.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        JsonObject object = response.body().get("result").getAsJsonObject();
                        JsonArray tags = object.get("result").getAsJsonArray();
                        if(tags.size() ==0){
                            Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
                            i.putExtra("keyword", mSearch.getText().toString());
                            startActivity(i);

                        }
                        else {
                            for (int j = 0; j < tags.size(); j++) {
                                mKeywords.add(tags.get(j).getAsJsonObject().get("keyword").getAsString());
                            }
                            Intent i = new Intent(getApplicationContext(), SearchTagActivity.class);
                            i.putExtra("keyword", mKeywords);
                            startActivity(i);

                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
                }
            });

//            Call<JsonObject> getPageCall = httpInterface.getPage(URLEncoder.encode(mSearch.getText().toString()));
//
//            getPageCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    try {
//                        JsonObject object = response.body().get("content").getAsJsonObject();
//                        JsonArray recipes = response.body().get("recipes").getAsJsonArray();
//                        JsonArray tags = response.body().get("tags").getAsJsonArray();
//                        if (object != null) {
//                            Log.d("<<<<", tags.toString());
//                            String keyword = object.get("keyword").getAsString();
//                            String ingredient = object.get("ingredient").getAsString();
//                            String category = object.get("category_con").getAsString();
//                            String category2 = object.get("category_cooking").getAsString();
//                            String creater = object.get("creater").getAsString();
//                            String updated_at = object.get("updated_at").getAsString();
//                            ArrayList<String> got_recipe = new ArrayList<String>();
//                            for(int j=0;j<recipes.size();j++){
//                                got_recipe.add(recipes.get(j).getAsJsonObject().get("descript").getAsString());
//                            }
//                            ArrayList<String> tags_got = new ArrayList<String>();
//                            for(int j=0;j<tags.size();j++){
//                                tags_got.add(tags.get(j).getAsJsonObject().get("tag").getAsString());
//                            }
//                            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
//                            i.putExtra("keyword", keyword);
//                            i.putExtra("ingredient", ingredient);
//                            i.putExtra("category", category);
//                            i.putExtra("category2", category2);
//                            i.putExtra("tags", tags_got);
//                            i.putExtra("creater", creater);
//                            i.putExtra("updated_at", updated_at);
//                            i.putExtra("recipes", got_recipe);
////                            ArrayList<String> got_recipe_image = new ArrayList<String>();
////
////                            for(int j=0;j<recipes.size();j++){
////                                if(recipes.get(j).getAsJsonObject().get("image") != null) {
////                                    got_recipe_image.add(recipes.get(j).getAsJsonObject().get("image").getAsString());
////                                    Log.d("dddd", "ddddd");
////                                }
////                            }
////                            i.putExtra("recipes_image", got_recipe_image);
//                            //Log.d("dddd","ddddd");
//
//                            startActivity(i);
//
//                        }
//                    }catch(Exception e){
//                        Intent i = new Intent(getApplicationContext(), EmptySearchActivity.class);
//                        i.putExtra("keyword", mSearch.getText().toString());
//                        startActivity(i);
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
//
//                }
//            });
        }
    });

}



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOncreate();

                } else {
                    setContentView(R.layout.permission_denied);
                }
                return;
            }
        }
    }


    public void doOncreate() {
        final TabHost mTab = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, FirstPage.class);
        spec = mTab.newTabSpec("a").setIndicator("", getResources().getDrawable(R.drawable.cloud)).setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, NoticeBoardActivity.class);
        spec = mTab.newTabSpec("b").setIndicator("", getResources().getDrawable(R.drawable.debate)).setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, CategoryActivity.class);
        spec = mTab.newTabSpec("c").setIndicator("", getResources().getDrawable(R.drawable.category)).setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, MyPageActivity.class);
        spec = mTab.newTabSpec("d").setIndicator("", getResources().getDrawable(R.drawable.personal)).setContent(intent);
        mTab.addTab(spec);


        setTabColor(mTab);
        mTab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {
                setTabColor(mTab);
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
//            tabhost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.round_tab); // unselected
            tabhost.getTabWidget().setStripEnabled(false);
        }
        tabhost.getTabWidget().setCurrentTab(0);
//        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
//                .setBackgroundResource(R.drawable.round_tab_white); // selected

    }


}

