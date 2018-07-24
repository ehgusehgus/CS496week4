package com.example.q.cs496_week4.MyPageActivity.myrecipe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.q.cs496_week4.R;

import java.util.ArrayList;

public class MyRecipe extends Activity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout swipeRefreshLayout;

    public MyRecipe() {
        //Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_myrecipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.myrecipe);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<MyRecipeItem> myRecipeItemArrayList = new ArrayList<>();

        MyRecipeAdapter myRecipeAdapter = new MyRecipeAdapter(myRecipeItemArrayList);
        mRecyclerView.setAdapter(myRecipeAdapter);



    }



}
