package com.example.q.cs496_week4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    Context mContext;

    TextView mKeyWord;
    String mCategory;
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
                String Ingredient = mIngredient.getText().toString();
                String category = mCategory;
                //reciepe get and get creator createdat updatedat
            }
        });

    }

    public void setRecipeAdpater(Context context, Activity activity) {
        mAdapter = new RecipeAdapter(recipes);
        mRecyclerView.setAdapter(mAdapter);
        return;
    }
}
