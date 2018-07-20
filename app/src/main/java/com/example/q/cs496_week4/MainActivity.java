package com.example.q.cs496_week4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;

public class MainActivity extends AppCompatActivity {

    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check login
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
