package com.example.q.cs496_week4.CookingCategoryActivity;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.q.cs496_week4.CountryCategoryActivity.CategoryItem;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import retrofit2.Callback;

@Layout(R.layout.category_child_layout1)
public class ChildView1 {
    private static String TAG ="ChildView";

    @View(R.id.child_keyword1)
    TextView textViewKeyword;

    @View(R.id.child_creater1)
    TextView textViewCreater;

    @View(R.id.child_image1)
    ImageView childImage;

    private Context mContext;
    private CategoryItem1 item;

    public ChildView1(Context mContext, CategoryItem1 item) {
        this.mContext = mContext;
        this.item = item;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        textViewKeyword.setText(item.getKeyword());

        Glide.with(mContext).load(HttpInterface.BaseURL+"images/"+item.getKeyword() +".jpg")
                .asBitmap()
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(childImage);
        //TODO: 사진 추가
    }
}