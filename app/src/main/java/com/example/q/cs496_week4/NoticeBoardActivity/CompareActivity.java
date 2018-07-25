package com.example.q.cs496_week4.NoticeBoardActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.facebook.AccessToken;
import com.google.gson.JsonObject;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompareActivity extends AppCompatActivity {

    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        accessToken = AccessToken.getCurrentAccessToken();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        final HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        final String keyword= getIntent().getExtras().getString("keyword");
        Log.d("TTTTTT", keyword);

        Call<JsonObject> getNoticeDetailCall = httpInterface.getNoticeDetail(URLEncoder.encode(keyword));
        getNoticeDetailCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("??????", response.body().toString());
                try {
                    try{

                    }catch (Exception e){

                    }

                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
            }
        });

        Call<JsonObject> getVoted = httpInterface.getVoted(URLEncoder.encode(keyword), accessToken.getUserId());
        getVoted.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    Integer check = response.body().get("result").getAsInt();
                    if(check==1)
                        findViewById(R.id.vote).setVisibility(View.GONE);
                    else
                        findViewById(R.id.vote).setVisibility(View.VISIBLE);
                }catch(Exception e){
                    Log.d("&&&", "&&&&");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
            }
        });

        Button agree_but = (Button) findViewById(R.id.agree);
        agree_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<JsonObject> Vote = httpInterface.Vote(keyword, accessToken.getUserId(), "1");
                Vote.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            findViewById(R.id.vote).setVisibility(View.GONE);
                        } catch (Exception e) {

                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button disagree_but = (Button) findViewById(R.id.disagree);
        agree_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<JsonObject> Vote = httpInterface.Vote(keyword, accessToken.getUserId(), "0");
                Vote.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        try {
                            findViewById(R.id.vote).setVisibility(View.GONE);
                        } catch (Exception e) {

                        }
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
