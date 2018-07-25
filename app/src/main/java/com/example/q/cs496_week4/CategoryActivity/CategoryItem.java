package com.example.q.cs496_week4.CategoryActivity;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {

    public String mKeyword;

    public String mCategory;

    //사진도 추가

    public CategoryItem(String keyword,String category) {
        this.mKeyword = keyword;
        this.mCategory = category;

    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }


}