package com.example.q.cs496_week4.CountryCategoryActivity;

public class CategoryItem {

    public String mKeyword;

    public String mCategory;

    public String imageUrl;
    //사진도 추가



    public CategoryItem(String keyword, String category) {
        this.mKeyword = keyword;
        this.mCategory = category;
        this.imageUrl = imageUrl;


    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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