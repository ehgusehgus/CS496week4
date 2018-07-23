package com.example.q.cs496_week4.NoticeBoardActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.cs496_week4.R;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private List<Notice> noticeList;

    @NonNull
    @Override
    public NoticeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notice_list_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.MyViewHolder myViewHolder, int i) {
        Notice notice = noticeList.get(i);
        myViewHolder.title.setText(notice.getKeyword());
        myViewHolder.genre.setText(notice.getNickname());
        myViewHolder.year.setText(notice.getDate());

    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            genre = itemView.findViewById(R.id.genre);
            year = itemView.findViewById(R.id.year);
        }
    }

    public NoticeAdapter(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }
}
