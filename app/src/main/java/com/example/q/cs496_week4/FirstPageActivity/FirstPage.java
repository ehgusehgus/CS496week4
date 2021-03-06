package com.example.q.cs496_week4.FirstPageActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstPage extends AppCompatActivity {

    Retrofit retrofit;
    HttpInterface httpInterface;
    ArrayList<SectionDataModel> allSampleData;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =this;
        setContentView(R.layout.activity_first_page);

        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        httpInterface = retrofit.create(HttpInterface.class);

        allSampleData = new ArrayList<>();

        new GetAsyncTast().execute();


    }

    private class GetAsyncTast extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            Call<JsonObject> getMainContentsCall = httpInterface.getRandomAndLatestContent();

            getMainContentsCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonArray randomArray = response.body().getAsJsonObject().get("random").getAsJsonArray();
                    JsonArray latestArray = response.body().getAsJsonObject().get("latest").getAsJsonArray();
                    JsonArray mostInterestArray = response.body().getAsJsonObject().get("mostInterest").getAsJsonArray();
                    if(randomArray != null && latestArray != null) {

                        allSampleData = new ArrayList<>();
                        SectionDataModel random = new SectionDataModel();
                        SectionDataModel latest = new SectionDataModel();
                        SectionDataModel mostInterest = new SectionDataModel();

                        random.setHeaderTitle("오늘의 메뉴");
                        latest.setHeaderTitle("최근 수정 레시피");
                        mostInterest.setHeaderTitle("인기 레시피");

                        ArrayList<CardItemModel> randomItem = new ArrayList<>();
                        ArrayList<CardItemModel> latestItem = new ArrayList<>();
                        ArrayList<CardItemModel> mostInterestItem = new ArrayList<>();

                        for(int i=0;i<randomArray.size();i++) {
                            JsonObject object = randomArray.get(i).getAsJsonObject();
                            randomItem.add(new CardItemModel(object.get("keyword").getAsString(), object.get("nickname").getAsString()));
                        }
                        for(int i=0;i<latestArray.size();i++){
                            JsonObject object = latestArray.get(i).getAsJsonObject();
                            latestItem.add(new CardItemModel(object.get("keyword").getAsString(), object.get("nickname").getAsString()));
                        }
                        for(int i=0;i<mostInterestArray.size();i++){
                            JsonObject object = mostInterestArray.get(i).getAsJsonObject();
                            mostInterestItem.add(new CardItemModel(object.get("keyword").getAsString(),""));
                        }

                        random.setAllItemsInSection(randomItem);
                        latest.setAllItemsInSection(latestItem);
                        mostInterest.setAllItemsInSection(mostInterestItem);

                        allSampleData.add(random);
                        allSampleData.add(latest);
                        allSampleData.add(mostInterest);
                        RecyclerView my_recycler_view = findViewById(R.id.my_recycler_view);
                        my_recycler_view.setHasFixedSize(true);
                        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(mContext, allSampleData);
                        my_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        my_recycler_view.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
            return null;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        new GetAsyncTast().execute();
    }


}
