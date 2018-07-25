package com.example.q.cs496_week4.CountryCategoryActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.q.cs496_week4.CookingCategoryActivity.CategoryActivity1;

public class CategoryTabs extends TabActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        TabHost mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("country").setIndicator("종류별").setContent(new Intent(this  ,CategoryActivity.class )));
        mTabHost.addTab(mTabHost.newTabSpec("cooking").setIndicator("방법별").setContent(new Intent(this , CategoryActivity1.class )));
        mTabHost.setCurrentTab(0);


    }
}
