package com.example.q.cs496_week4.NoticeBoardActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.q.cs496_week4.DetailSearchActivity.SearchActivity;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.MyApplication;
import com.example.q.cs496_week4.R;
import com.example.q.cs496_week4.UserActivity.UserCreateActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NoticeBoardActivity extends AppCompatActivity {

    private List<Notice> noticeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoticeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_content_main);

        recyclerView = findViewById(R.id.recycler_view_notice);
        mAdapter = new NoticeAdapter(noticeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Notice notice = noticeList.get(position);
                Intent i = new Intent(getApplicationContext(), CompareActivity.class);
                Toast.makeText(getApplicationContext(), notice.getKeyword() + " is selected!", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareNoticeData();

    }
    private void prepareNoticeData() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        Call<JsonObject> getNoticeListCall = httpInterface.getNoticeList();
        getNoticeListCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray array = response.body().get("result").getAsJsonArray();
                for(int i=0;i<array.size();i++){
                    JsonObject object = array.get(i).getAsJsonObject();
                    Log.d("NOTICE", object.toString());
                    //TODO: date 형식 변경후 출력
                    Notice notice = new Notice(object.get("keyword").toString().replace("\"",""), "", "");
                    noticeList.add(notice);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
            }
        });
    }

}
