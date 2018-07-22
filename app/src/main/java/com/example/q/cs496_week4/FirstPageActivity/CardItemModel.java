package com.example.q.cs496_week4.FirstPageActivity;

public class CardItemModel {

    private String keyword;
    private String creater;

    public CardItemModel(String keyword, String creater){
        this.keyword = keyword;
        this.creater = creater;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
