package com.example.q.cs496_week4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditActivity extends AppCompatActivity {

    Context mContext;

    TextView mKeyWord;
    String mCategory="";
    TextView mIngredient;
    TextView mRecipe;
    ArrayList<String> recipes = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mKeyWord = (TextView) findViewById(R.id.keyword);
        mIngredient = (TextView) findViewById(R.id.ingrediant);
        mRecipe = (TextView) findViewById(R.id.recipe_desc);


        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setRecipeAdpater(this, this);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // TODO Auto-generated method stub
                        final int mposition = position;
                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(EditActivity.this);
                        View promptsView = li.inflate(R.layout.prompts, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                EditActivity.this);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                // get user input and set it to
                                                // result
                                                // edit text

                                                recipes.set(mposition, userInput.getText().toString());
                                                setRecipeAdpater(EditActivity.this,EditActivity.this);
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {
                                                dialog.cancel();
                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                })
        );


        final Button bt_category = (Button) findViewById(R.id.category);
        Button bt_recipeadd = (Button) findViewById(R.id.recipe_add);
        Button bt_finish = (Button) findViewById(R.id.finish);

        bt_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = new String[]{"KOR", "WEST", "CHN", "JPN", "ETC"};
                final int[] selectedIndex = {0};

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                dialog .setTitle("Choose Category")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedIndex[0] = which;
                            }
                        })

                        .setPositiveButton("apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(selectedIndex[0] == 0){
                                    bt_category.setText(items[0]);
                                    mCategory = items[0];
                                }
                                if(selectedIndex[0] == 1){
                                    bt_category.setText(items[1]);
                                    mCategory = items[1];
                                }
                                if(selectedIndex[0] == 2){
                                    bt_category.setText(items[2]);
                                    mCategory = items[2];
                                }
                                if(selectedIndex[0] == 3){
                                    bt_category.setText(items[3]);
                                    mCategory = items[3];
                                }
                                if(selectedIndex[0] == 4){
                                    bt_category.setText(items[4]);
                                    mCategory = items[4];
                                }
                            }
                        }).create().show();
            }
        });

        bt_recipeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add recipe and reset adapter
                String Recipedesc = mRecipe.getText().toString();
                recipes.add(Recipedesc);
                setRecipeAdpater(mContext, EditActivity.this);
        }
    });

        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Keyword = mKeyWord.getText().toString();
                if(Keyword.equals("")){
                    Toast.makeText(getApplication(), "Write KeyWord!!", Toast.LENGTH_LONG).show();
                    return;
                }

                String Ingredient = mIngredient.getText().toString();
                if(Ingredient.equals("")){
                    Toast.makeText(getApplication(), "Write Ingredient!!", Toast.LENGTH_LONG).show();
                    return;
                }
                String category = mCategory;
                if(category.equals("")){
                    Toast.makeText(getApplication(), "Choose Category!!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(recipes.size() == 0){
                    Toast.makeText(getApplication(), "Write Recipe!!", Toast.LENGTH_LONG).show();
                    return;
                }
                MyApplication myapp = (MyApplication) getApplication();
                JsonArray jsonarray = new JsonArray();

                for(int i=0; i<recipes.size(); i++){
                    JsonObject inter = new JsonObject();
                    try{
                        inter.addProperty("index", (i+1)+"");
                        inter.addProperty("descript", recipes.get(i));
                        jsonarray.add(inter);
                    }catch(JsonIOException e){
                        e.printStackTrace();
                    }
                }

                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(HttpInterface.BaseURL)
                        .build();
                HttpInterface httpInterface = retrofit.create(HttpInterface.class);
                Log.d("JSOSOSOSO", jsonarray+"");

                AccessToken a = AccessToken.getCurrentAccessToken();

                Call<JsonObject> editPage = httpInterface.editPage(Keyword, Ingredient , a.getUserId(), category, jsonarray.toString());
                editPage.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        finish();
                        }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public void setRecipeAdpater(Context context, Activity activity) {
        mAdapter = new RecipeAdapter(recipes, context);
        mRecyclerView.setAdapter(mAdapter);
        return;
    }
}
