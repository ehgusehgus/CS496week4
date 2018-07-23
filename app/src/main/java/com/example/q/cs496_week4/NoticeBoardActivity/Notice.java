package com.example.q.cs496_week4.NoticeBoardActivity;

import java.util.Date;

public class Notice {

    private String keyword;
    private String date;
    private String nickname;

    public Notice(String keyword, String date, String nickname){
        this.keyword = keyword;
        this.date = date;
        this.nickname = nickname;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
