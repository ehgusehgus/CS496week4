package com.example.q.cs496_week4.CategoryActivity;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {



    @SerializedName("keyword")
    private static String keyword;


    @SerializedName("category_con")
    private static String category;

    //사진도 추가

    public CategoryItem(String keyword,String category) {
        this.keyword = keyword;
        this.category = category;

    }

    public static String getCategory() {
        return category;
    }

    public static void setCategory(String category) {
        CategoryItem.category = category;
    }
    public static String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }



    @Override
    public String toString() {
        return "CategoryItem{" +
                "keyword='" +keyword+"\'" +
                ", category='" + category + '\'' +
                '}';
    }
}