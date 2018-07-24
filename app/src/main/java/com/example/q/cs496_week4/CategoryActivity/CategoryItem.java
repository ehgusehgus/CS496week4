package com.example.q.cs496_week4.CategoryActivity;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {

    @SerializedName("keyword")
    private static String keyword;

    @SerializedName("creater")
    private static String creater;

    @SerializedName("category")
    private static String category;

    //사진도 추가



    public CategoryItem(String keyword) {
        this.keyword = keyword;
        this.creater = creater;
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

    public static String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "keyword='" +keyword+"\'" +
                ", creater='" + creater +'\'' +
                ", category='" + category + '\'' +
                '}';
    }
}