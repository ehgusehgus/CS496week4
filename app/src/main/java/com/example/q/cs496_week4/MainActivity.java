package com.example.q.cs496_week4;

import android.Manifest;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.q.cs496_week4.UserActivity.LoginActivity;
import com.example.q.cs496_week4.UserActivity.UserCreateActivity;
import com.facebook.AccessToken;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends TabActivity {


    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check login
        accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken == null || accessToken.isExpired()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        if(MyApplication.nickname.equals("")) {
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(HttpInterface.BaseURL)
                    .build();
            HttpInterface httpInterface = retrofit.create(HttpInterface.class);
            Call<JsonObject> getUserCall = httpInterface.getUser(accessToken.getUserId());
            getUserCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject object = response.body().get("result").getAsJsonObject();
                    if (object != null) {
                        if(object.get("nickname") == null){
                            Intent intent = new Intent(getApplication(),UserCreateActivity.class);
                            startActivity(intent);
                        } else {
                            String res = object.get("nickname").toString();
                            Toast.makeText(getApplication(), res, Toast.LENGTH_LONG).show();
                            MyApplication.setNickname(object.get("nickname").toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();

                }
            });
        }
            // Here, thisActivity is the current activity
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS,
                        0);
            } else {
                doOncreate();
            }

            doOncreate();

            Button edit_but = (Button) findViewById(R.id.edit_but);

            edit_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), EditActivity.class);
                    startActivity(i);
                }
            });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOncreate();

                } else {
                    setContentView(R.layout.permission_denied);
                }
                return;
            }
        }
    }


    public void doOncreate() {
        final TabHost mTab = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, NoticeBoardActivity.class);
        spec = mTab.newTabSpec("Contact").setIndicator("").setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, NoticeBoardActivity.class);
        spec = mTab.newTabSpec("Contact").setIndicator("").setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, NoticeBoardActivity.class);
        spec = mTab.newTabSpec("Contact").setIndicator("").setContent(intent);
        mTab.addTab(spec);
        intent = new Intent(this, NoticeBoardActivity.class);
        spec = mTab.newTabSpec("Contact").setIndicator("").setContent(intent);
        mTab.addTab(spec);
//
//        intent = new Intent(this, Tab2.class);
//        spec = mTab.newTabSpec("Gallery").setIndicator("", getResources().getDrawable(R.drawable.gallery)).setContent(intent);
//        mTab.addTab(spec);
//
//        intent = new Intent(this, DrawingActivity.class);
//        spec = mTab.newTabSpec("tab3").setIndicator("", getResources().getDrawable(R.drawable.paint)).setContent(intent);
//        mTab.addTab(spec);

        setTabColor(mTab);
        mTab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {
                setTabColor(mTab);
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
//            tabhost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.round_tab); // unselected
            tabhost.getTabWidget().setStripEnabled(false);
        }
        tabhost.getTabWidget().setCurrentTab(0);
//        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
//                .setBackgroundResource(R.drawable.round_tab_white); // selected

    }
}

