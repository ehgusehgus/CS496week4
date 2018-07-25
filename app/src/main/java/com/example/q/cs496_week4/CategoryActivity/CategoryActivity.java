package com.example.q.cs496_week4.CategoryActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private Map<String,List<CategoryItem>> categoryMap;

    private List<CategoryItem> categoryItems;

    private ExpandablePlaceHolderView expandablePlaceHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryItems = new ArrayList<>();
        categoryMap = new HashMap<>();
        expandablePlaceHolderView = (ExpandablePlaceHolderView) findViewById(R.id.expandablePlaceHolder);

        loadData();

        expandablePlaceHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked", view.getId()).show();
            }
        });

    }

    private void loadData(){

        final Object country[] =new Object[]{"KOR","WEST","CHN","JPN","ETC"};
        final Object countryfullname[] = new Object[]{"한식","양식","중식","일식","기타"};
        HttpInterface httpInterface = CategoryApi.getRetrofit().create(HttpInterface.class);

        for(int j=0;j<country.length;j++) {

            final int finalJ = j;
            httpInterface.getCategory(country[j].toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonArray object = response.body().get("result").getAsJsonArray();
                    Log.d("ppp",object.toString());

                    expandablePlaceHolderView.addView(new HeaderView(this , countryfullname[finalJ].toString() ));
                    //CategoryItem[] categoryItem = new CategoryItem[object.size()];
                    ArrayList<CategoryItem> categoryMap = new ArrayList<>();

                    for (int i = 0; i < object.size(); i++) {
                        //JsonObject item;
                        JsonObject item = (JsonObject) object.get(i);

                        CategoryItem categoryItem1 = new CategoryItem(null,null);
                        categoryItem1.setKeyword(item.get("keyword").getAsString());
                        categoryItem1.setCategory(countryfullname[finalJ].toString());

                        categoryMap.add(categoryItem1);
                        //CategoryItem categoryItem1 = new CategoryItem(item.get("keyword").getAsString(), countryfullname[finalJ].toString());
                        for(int k=0; k< categoryMap.size(); k++){
                            Log.d("adf", categoryMap.get(k).toString());

                        }
                        //categoryMap.add(categoryItem1);
                        //new CategoryItem(item.get("keyword").getAsString(), countryfullname[finalJ].toString())
                    }

                    Log.d("qqq1", String.valueOf(categoryMap.size()));

                    for(int k=0; k< categoryMap.size(); k++){
                        Log.d("qqq2", categoryMap.get(k).toString());
                    }

                    for(CategoryItem categoryItem : categoryMap) {
                        expandablePlaceHolderView.addView(new ChildView(this, categoryItem));
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        }


    }

}