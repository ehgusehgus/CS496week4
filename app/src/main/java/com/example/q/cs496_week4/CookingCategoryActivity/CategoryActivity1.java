package com.example.q.cs496_week4.CookingCategoryActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity1 extends AppCompatActivity {

    private Map<String,List<CategoryItem1>> categoryMap;

    private List<CategoryItem1> categoryItems;
    private Context mContext;
    private ExpandablePlaceHolderView expandablePlaceHolderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category1);

        mContext=this;
        categoryItems = new ArrayList<>();
        categoryMap = new HashMap<>();
        expandablePlaceHolderView = (ExpandablePlaceHolderView) findViewById(R.id.expandablePlaceHolder1);

        loadData();

        expandablePlaceHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked", view.getId()).show();
            }
        });

    }

    private void loadData(){
        final Object cooking[] =new Object[]{"밥", "면", "국물", "찜/조림", "구이", "볶음", "튀김/부침", "나물", "디저트", "기타"};

        HttpInterface httpInterface = CategoryApi1.getRetrofit1().create(HttpInterface.class);

        for(int j=0;j<cooking.length;j++) {

            final int finalJ = j;
            httpInterface.getCategory1(URLEncoder.encode(cooking[j].toString())).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonArray object = response.body().get("result").getAsJsonArray();
                    Log.d("ppp",object.toString());

                    expandablePlaceHolderView.addView(new HeaderView1(mContext , cooking[finalJ].toString() ));

                    //CategoryItem[] categoryItem = new CategoryItem[object.size()];
                    ArrayList<CategoryItem1> categoryMap = new ArrayList<>();

                    for (int i = 0; i < object.size(); i++) {
                        //JsonObject item;
                        JsonObject item = (JsonObject) object.get(i);

                        CategoryItem1 categoryItem1 = new CategoryItem1(null,null);
                        categoryItem1.setKeyword(item.get("keyword").getAsString());
                        categoryItem1.setCategory(cooking[finalJ].toString());

                        categoryMap.add(categoryItem1);
                    }

                    Log.d("qqq1", String.valueOf(categoryMap.size()));


                    for(CategoryItem1 categoryItem1 : categoryMap) {
                        expandablePlaceHolderView.addView(new ChildView1(mContext, categoryItem1));
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });
        }


    }

}
