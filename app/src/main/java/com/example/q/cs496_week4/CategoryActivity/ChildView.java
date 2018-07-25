package com.example.q.cs496_week4.CategoryActivity;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

    public ChildView(Callback<JsonObject> mContext, CategoryItem item) {
        this.item = item;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        textViewKeyword.setText(CategoryItem.getKeyword());
        //TODO: 사진 추가
    }
}
