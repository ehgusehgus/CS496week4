package com.example.q.cs496_week4.NoticeBoardActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.facebook.AccessToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompareActivity extends AppCompatActivity {

    AccessToken accessToken;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        accessToken = AccessToken.getCurrentAccessToken();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpInterface.BaseURL)
                .build();
        final HttpInterface httpInterface = retrofit.create(HttpInterface.class);

        final String keyword= getIntent().getExtras().getString("keyword");
        Log.d("TTTTTT", keyword);


        final ArrayList<Model3> list= new ArrayList();
        list.add(new Model3(Model3.TEXT_TYPE,"요리이름",keyword,"",null));

//        list.add(new Model2(Model2.SEARCH_IMAGE_TYPE,"REPRESENTATIVE IMAGE",keyword,recipes, null));
//        list.add(new Model2(Model2.SEARCH_KEYWORD_TYPE,"RECIPE","",recipes,null));
//        for(int j=0;j<recipes.size();j++){
//            list.add(new Model2(Model2.SEARCH_RECIPE_TYPE,(j+1)+"",recipes.get(j),recipes,keyword));
//        }
//

        Call<JsonObject> getNoticeDetailCall = httpInterface.getNoticeDetail(URLEncoder.encode(keyword));
        getNoticeDetailCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("??????", response.body().toString());
                try {
                    JsonObject object = response.body().get("category_con").getAsJsonObject();
                    Boolean check = object.get("check").getAsBoolean();
                    if(check)
                        list.add(new Model3(Model3.DIFF_TEXT_TYPE,"나라별요리",object.get("before").getAsString(),object.get("after").getAsString(),null));
                    else
                        list.add(new Model3(Model3.TEXT_TYPE,"나라별요리",object.get("before").getAsString(),"",null));

                    JsonObject object2 = response.body().get("category_cooking").getAsJsonObject();
                    Boolean check2 = object2.get("check").getAsBoolean();
                    if(check2)
                        list.add(new Model3(Model3.DIFF_TEXT_TYPE,"조리방법",object2.get("before").getAsString(),object2.get("after").getAsString(),null));
                    else
                        list.add(new Model3(Model3.TEXT_TYPE,"조리방법",object2.get("before").getAsString(),"",null));

                    JsonObject object3 = response.body().get("ingredient").getAsJsonObject();
                    Boolean check3 = object3.get("check").getAsBoolean();
                    if(check3)
                        list.add(new Model3(Model3.DIFF_TEXT_TYPE,"재료",object3.get("before").getAsString(),object3.get("after").getAsString(),null));
                    else
                        list.add(new Model3(Model3.TEXT_TYPE,"재료",object3.get("before").getAsString(),"",null));

                    JsonObject object4 = response.body().get("tags").getAsJsonObject();
                    Boolean check4 = object4.get("check").getAsBoolean();
                    JsonArray prev_tags = object4.get("before").getAsJsonArray();
                    String prev_tag="";
                    for(int i=0;i<prev_tags.size()-1;i++)
                        prev_tag = prev_tag+ prev_tags.get(i).getAsJsonObject().get("tag").getAsString() +", ";
                    if(prev_tags.size()>=1)
                        prev_tag = prev_tag + prev_tags.get(prev_tags.size()-1).getAsJsonObject().get("tag").getAsString();
                    String post_tag="";
                    Log.d("?????", prev_tag);
                    if(check4){
                        JsonArray post_tags = object4.get("after").getAsJsonArray();
                        for(int i=0;i<post_tags.size()-1;i++)
                            post_tag = post_tag+ post_tags.get(i).getAsJsonObject().get("tag").getAsString() +", ";
                        if(post_tags.size()>=1)
                            post_tag = post_tag + post_tags.get(post_tags.size()-1).getAsJsonObject().get("tag").getAsString();
                    }

                    if(check4)
                        list.add(new Model3(Model3.DIFF_TEXT_TYPE,"달러태그",prev_tag,post_tag,null));
                    else
                        list.add(new Model3(Model3.TEXT_TYPE,"달러태그",prev_tag,"",null));

                    JsonObject object5 = response.body().get("recipes").getAsJsonObject();
                    Boolean check5 = object5.get("check").getAsBoolean();
                    JsonArray prev_recipes = object5.get("before").getAsJsonArray();
                    String prev="";
                    for(int i=0;i<prev_recipes.size();i++)
                        prev = prev+ (i+1)+". "+ prev_recipes.get(i).getAsJsonObject().get("descript").getAsString() +"\n";
                    String post="";
                    if(check5){
                        JsonArray post_recipes = object5.get("after").getAsJsonArray();
                        for(int i=0;i<post_recipes.size();i++)
                            post = post+ (i+1)+". "+ post_recipes.get(i).getAsJsonObject().get("descript").getAsString() +"\n";
                    }

                    if(check5)
                        list.add(new Model3(Model3.DIFF_TEXT_TYPE,"레시피",prev,post,null));
                    else
                        list.add(new Model3(Model3.TEXT_TYPE,"레시피",prev,"",null));


                    MultiViewTypeAdapter3 adapter = new MultiViewTypeAdapter3(list,mContext);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);

                    RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview3);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.setAdapter(adapter);

                }catch(Exception e){
                    Log.d("fsdfsfsdfsdf","dfsffddf");

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
        disagree_but.setOnClickListener(new View.OnClickListener() {
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
