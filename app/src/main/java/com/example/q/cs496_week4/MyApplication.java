package com.example.q.cs496_week4;

import android.app.Application;

public class MyApplication extends Application {
    public static String nickname = "";


    public String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        MyApplication.nickname = nickname;
    }

}
