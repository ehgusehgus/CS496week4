package com.example.q.cs496_week4.CountryCategoryActivity;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.q.cs496_week4.CookingCategoryActivity.CategoryItem1;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import retrofit2.Callback;

@Layout(R.layout.category_child_layout)
public class ChildView {
    private static String TAG ="ChildView";

    @View(R.id.child_keyword)
    TextView textViewKeyword;

    @View(R.id.child_creater)
    TextView textViewCreater;

    @View(R.id.child_image)
    ImageView childImage;

    private Context mContext;
    private CategoryItem item;

    public ChildView(Context mContext, CategoryItem item) {
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
