package com.example.q.cs496_week4.NoticeBoardActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.q.cs496_week4.R;

import java.util.ArrayList;
import java.util.List;

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
                Toast.makeText(getApplicationContext(), notice.getKeyword() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareNoticeData();

    }
    private void prepareNoticeData() {
        Notice notice = new Notice("Mad Max: Fury Road", "Action & Adventure", "2015");
        noticeList.add(notice);

        notice = new Notice("Inside Out", "Animation, Kids & Family", "2015");
        noticeList.add(notice);

        notice = new Notice("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        noticeList.add(notice);

        notice = new Notice("Shaun the Sheep", "Animation", "2015");
        noticeList.add(notice);

        notice = new Notice("The Martian", "Science Fiction & Fantasy", "2015");
        noticeList.add(notice);

        notice = new Notice("Mission: Impossible Rogue Nation", "Action", "2015");
        noticeList.add(notice);

        notice = new Notice("Up", "Animation", "2009");
        noticeList.add(notice);

        notice = new Notice("Star Trek", "Science Fiction", "2009");
        noticeList.add(notice);

        notice = new Notice("The LEGO Notice", "Animation", "2014");
        noticeList.add(notice);

        notice = new Notice("Iron Man", "Action & Adventure", "2008");
        noticeList.add(notice);

        notice = new Notice("Aliens", "Science Fiction", "1986");
        noticeList.add(notice);

        notice = new Notice("Chicken Run", "Animation", "2000");
        noticeList.add(notice);

        notice = new Notice("Back to the Future", "Science Fiction", "1985");
        noticeList.add(notice);

        notice = new Notice("Raiders of the Lost Ark", "Action & Adventure", "1981");
        noticeList.add(notice);

        notice = new Notice("Goldfinger", "Action & Adventure", "1965");
        noticeList.add(notice);

        notice = new Notice("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        noticeList.add(notice);

        mAdapter.notifyDataSetChanged();
    }

}
