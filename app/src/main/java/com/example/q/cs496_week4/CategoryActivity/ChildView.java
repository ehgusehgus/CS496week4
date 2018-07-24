package com.example.q.cs496_week4.CategoryActivity;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.cs496_week4.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

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
        textViewKeyword.setText(CategoryItem.getKeyword());
        textViewCreater.setText(CategoryItem.getCreater());
        //TODO: 사진 추가
    }
}
