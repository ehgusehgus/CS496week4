package com.example.q.cs496_week4.CookingCategoryActivity;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.q.cs496_week4.R;
import com.google.gson.JsonObject;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

import retrofit2.Callback;

@Parent
@SingleTop
@Layout(R.layout.category_header_layout1)
public class HeaderView1 {
    private static String TAG = "HeaderView";

    @View(R.id.header_text1)
    TextView headerText;

    private Context mContext;
    private String mHeaderText;

    public HeaderView1(Context context, String headerText) {
        this.mContext = context;
        this.mHeaderText = headerText;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG, "onResolve");
        headerText.setText(mHeaderText);
    }

    @Expand
    private void onExpand(){
        Log.d(TAG, "onExpand");
    }

    @Collapse
    private void onCollapse(){
        Log.d(TAG, "onCollapse");
    }
}

