package com.example.q.cs496_week4;

import android.app.Application;
import android.os.StrictMode;

public class MyApplication extends Application {
    public static String nickname = "";

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        MyApplication.nickname = nickname;
    }

}
