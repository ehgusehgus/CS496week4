package com.example.q.cs496_week4.CategoryActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
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

        HttpInterface httpInterface = CategoryApi.getRetrofit().create(HttpInterface.class);
        httpInterface.getCategory().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                categoryItems = response.body();
                getHeaderAndChild(categoryItems);
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {

            }
        });

    }

    private void getHeaderAndChild(List<CategoryItem> categoryItems){

        for (CategoryItem categoryItem : categoryItems ){
            List<CategoryItem> categoryItems1 = categoryMap.get(categoryItem.getCategory());
            if(categoryItems1 == null){
                categoryItems1 = new ArrayList<>();
            }
            categoryItems1.add(categoryItem);
            categoryMap.put(categoryItem.getCategory(),categoryItems1);
        }

        Log.d("Map",categoryMap.toString());
        Iterator it = categoryMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("Key", pair.getKey().toString());
            expandablePlaceHolderView.addView(new HeaderView(this, pair.getKey().toString()));
            List<CategoryItem> categoryItems1 = (List<CategoryItem>) pair.getValue();
            for (CategoryItem categoryItem : categoryItems1){
                expandablePlaceHolderView.addView(new ChildView(this, categoryItem));
            }
            it.remove();
        }
    }



}
