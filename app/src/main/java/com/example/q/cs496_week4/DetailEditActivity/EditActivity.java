package com.example.q.cs496_week4.DetailEditActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.q.cs496_week4.DetailSearchActivity.Model2;
import com.example.q.cs496_week4.R;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    Context mContext;
    Boolean is_first = false;
    TextView mKeyWord;
    String mCategory = "";
    String mCategory2 = "";
    TextView mIngredient;
    TextView mRecipe;
    ArrayList<String> recipes = new ArrayList<String>();
    MultiViewTypeAdapter mAdapter;

    //    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

//        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String keyword_got = extras.getString("keyword");
        String ingredient_got = extras.getString("ingredient");
        String category_got = extras.getString("category");
        String category_got2 = extras.getString("category2");
        String tag_got = extras.getString("tag");
        String creater_got = extras.getString("creater");
        String updated_got = extras.getString("updated_at");
        ArrayList<String> recipes_got = extras.getStringArrayList("recipes");

        if(ingredient_got.equals(""))
            is_first = true;

        ArrayList<Model> list = new ArrayList();
        if(keyword_got.equals(""))
            list.add(new Model(Model.EDIT_KEYWORD_TYPE, "요리이름", keyword_got, null, null));
        else
            list.add(new Model(Model.EDIT_KEYWORD_TYPE2, "요리이름", keyword_got, null, null));

        list.add(new Model(Model.EDIT_CATEGORY_TYPE, "나라별요리", category_got, null, null));
        list.add(new Model(Model.EDIT_CATEGORY2_TYPE, "조리방법", category_got2, null, null));
        list.add(new Model(Model.EDIT_INGREDIENT_TYPE, "재료", ingredient_got, null, null));
        list.add(new Model(Model.EDIT_TAG_TYPE, "달러태그", tag_got, null, null));
        list.add(new Model(Model.EDIT_IMAGE_TYPE, "대표사진", "", null, null));
        list.add(new Model(Model.EDIT_LISTVIEW_TYPE, "레시피", "", recipes_got, null));
        for(int j=0;j<recipes_got.size();j++){
            list.add(new Model(Model.EDIT_RECIPE_TYPE,(j+1)+"",recipes_got.get(j),recipes_got,null));
        }

        mAdapter = new MultiViewTypeAdapter(list, this, is_first);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAdapter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuItem edit = menu.add(Menu.NONE, R.id.done, 10, R.string.done);
        edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        edit.setIcon(R.drawable.cloud);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.done:
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                ((MultiViewTypeAdapter)recyclerView.getAdapter()).finishClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


//        mKeyWord = (TextView) findViewById(R.id.keyword);
//        mKeyWord.setText(keyword_got);
//        mIngredient = (TextView) findViewById(R.id.ingrediant);
//        mIngredient.setText(ingredient_got);
//        mRecipe = (TextView) findViewById(R.id.recipe_desc);
//        recipes = recipes_got;

//        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_view);
//        mRecyclerView.setHasFixedSize(true);
//
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

//        mListView =(ListView) findViewById(R.id.listview);
//
//        setRecipeAdpater(this, this);
//
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//                                           int pos, long id) {
//                final int mposition = pos;
//                        // get prompts.xml view
//                        LayoutInflater li = LayoutInflater.from(EditActivity.this);
//                        View promptsView = li.inflate(R.layout.prompts, null);
//
//                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                                EditActivity.this);
//
//                        // set prompts.xml to alertdialog builder
//                        alertDialogBuilder.setView(promptsView);
//
//                        final EditText userInput = (EditText) promptsView
//                                .findViewById(R.id.editTextDialogUserInput);
//
//                        // set dialog message
//                        alertDialogBuilder
//                                .setCancelable(true)
//                                .setPositiveButton("Edit",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int id) {
//                                                // get user input and set it to
//                                                // result
//                                                // edit text
//                                                if(userInput.getText().toString().equals("")){
//                                                    Toast.makeText(getApplication(), "Write Description!!", Toast.LENGTH_LONG).show();
//                                                    return;
//                                                }
//                                                recipes.set(mposition, userInput.getText().toString());
//                                                setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                            }
//                                        })
//                                .setNegativeButton("DELETE",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int id) {
//                                                recipes.remove(mposition);
//                                                setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                        alertDialogBuilder.setNeutralButton("INSERT",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(
//                                            DialogInterface dialog,
//                                            int id) {
//                                        // get user input and set it to
//                                        // result
//                                        // edit text
//                                        if(userInput.getText().toString().equals("")){
//                                            Toast.makeText(getApplication(), "Write Description!!", Toast.LENGTH_LONG).show();
//                                            return;
//                                        }
//                                        recipes.add(mposition+1,userInput.getText().toString());
//                                        setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                    }
//                                });
//
//
//
//                        // create alert dialog
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//
//                        // show it
//                        alertDialog.show();
//
//
//                return true;
//            }
//        });
//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(mContext, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        // do whatever
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // TODO Auto-generated method stub
//                        final int mposition = position;
//                        // get prompts.xml view
//                        LayoutInflater li = LayoutInflater.from(EditActivity.this);
//                        View promptsView = li.inflate(R.layout.prompts, null);
//
//                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                                EditActivity.this);
//
//                        // set prompts.xml to alertdialog builder
//                        alertDialogBuilder.setView(promptsView);
//
//                        final EditText userInput = (EditText) promptsView
//                                .findViewById(R.id.editTextDialogUserInput);
//
////                        final Button insert_btn = (Button) promptsView.findViewById(R.id.insert);
////
////                        insert_btn.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                recipes.remove(mposition);
////                                setRecipeAdpater(EditActivity.this,EditActivity.this);
////                            }
////                        });
//
//                        // set dialog message
//                        alertDialogBuilder
//                                .setCancelable(true)
//                                .setPositiveButton("Edit",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int id) {
//                                                // get user input and set it to
//                                                // result
//                                                // edit text
//                                                if(userInput.getText().toString().equals("")){
//                                                    Toast.makeText(getApplication(), "Write Description!!", Toast.LENGTH_LONG).show();
//                                                    return;
//                                                }
//                                                recipes.set(mposition, userInput.getText().toString());
//                                                setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                            }
//                                        })
//                                .setNegativeButton("DELETE",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int id) {
//                                                recipes.remove(mposition);
//                                                setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                        alertDialogBuilder.setNeutralButton("INSERT",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(
//                                            DialogInterface dialog,
//                                            int id) {
//                                        // get user input and set it to
//                                        // result
//                                        // edit text
//                                        if(userInput.getText().toString().equals("")){
//                                            Toast.makeText(getApplication(), "Write Description!!", Toast.LENGTH_LONG).show();
//                                            return;
//                                        }
//                                        recipes.add(mposition+1,userInput.getText().toString());
//                                        setRecipeAdpater(EditActivity.this,EditActivity.this);
//                                    }
//                                });
//
//
//
//                        // create alert dialog
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//
//                        // show it
//                        alertDialog.show();
//                    }
//                })
//        );


//        final Button bt_category = (Button) findViewById(R.id.category);
//        Button bt_recipeadd = (Button) findViewById(R.id.recipe_add);
//        Button bt_finish = (Button) findViewById(R.id.finish);
//        if(!category_got.equals("")){
//            bt_category.setText(category_got);
//        }
//        bt_category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String[] items = new String[]{"KOR", "WEST", "CHN", "JPN", "ETC"};
//                final int[] selectedIndex = {0};
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
//                dialog .setTitle("Choose Category")
//                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                selectedIndex[0] = which;
//                            }
//                        })
//
//                        .setPositiveButton("apply", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(selectedIndex[0] == 0){
//                                    bt_category.setText(items[0]);
//                                    mCategory = items[0];
//                                }
//                                if(selectedIndex[0] == 1){
//                                    bt_category.setText(items[1]);
//                                    mCategory = items[1];
//                                }
//                                if(selectedIndex[0] == 2){
//                                    bt_category.setText(items[2]);
//                                    mCategory = items[2];
//                                }
//                                if(selectedIndex[0] == 3){
//                                    bt_category.setText(items[3]);
//                                    mCategory = items[3];
//                                }
//                                if(selectedIndex[0] == 4){
//                                    bt_category.setText(items[4]);
//                                    mCategory = items[4];
//                                }
//                            }
//                        }).create().show();
//            }
//        });
//
//        final Button bt_category2 = (Button) findViewById(R.id.category2);
//        if(!category_got2.equals("")){
//            bt_category2.setText(category_got2);
//        }
//        bt_category2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String[] items = new String[]{"밥", "면", "국물", "찜/조림", "구이","볶음","튀김/부침","나물","디저트","기타"};
//                final int[] selectedIndex = {0};
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
//                dialog .setTitle("Choose Category")
//                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                selectedIndex[0] = which;
//                            }
//                        })
//
//                        .setPositiveButton("apply", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(selectedIndex[0] == 0){
//                                    bt_category2.setText(items[0]);
//                                    mCategory2 = items[0];
//                                }
//                                if(selectedIndex[0] == 1){
//                                    bt_category2.setText(items[1]);
//                                    mCategory2 = items[1];
//                                }
//                                if(selectedIndex[0] == 2){
//                                    bt_category2.setText(items[2]);
//                                    mCategory2 = items[2];
//                                }
//                                if(selectedIndex[0] == 3){
//                                    bt_category2.setText(items[3]);
//                                    mCategory2 = items[3];
//                                }
//                                if(selectedIndex[0] == 4){
//                                    bt_category2.setText(items[4]);
//                                    mCategory2 = items[4];
//                                }
//                                if(selectedIndex[0] == 5){
//                                    bt_category2.setText(items[5]);
//                                    mCategory2 = items[5];
//                                }
//                                if(selectedIndex[0] == 6){
//                                    bt_category2.setText(items[6]);
//                                    mCategory2 = items[6];
//                                }
//                                if(selectedIndex[0] == 7){
//                                    bt_category2.setText(items[7]);
//                                    mCategory2 = items[7];
//                                }
//                                if(selectedIndex[0] == 8){
//                                    bt_category2.setText(items[8]);
//                                    mCategory2 = items[8];
//                                }
//                                if(selectedIndex[0] == 9){
//                                    bt_category2.setText(items[9]);
//                                    mCategory2 = items[9];
//                                }
//                            }
//                        }).create().show();
//            }
//        });
//
//
//
//        bt_recipeadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //add recipe and reset adapter
//                String Recipedesc = mRecipe.getText().toString();
//                if(Recipedesc.equals("")){
//                    Toast.makeText(getApplication(), "Write Description!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                recipes.add(Recipedesc);
//                setRecipeAdpater(mContext, EditActivity.this);
//                mRecipe.setText("");
//        }
//    });
//
//        bt_finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Keyword = mKeyWord.getText().toString();
//                if(Keyword.equals("")){
//                    Toast.makeText(getApplication(), "Write KeyWord!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                String Ingredient = mIngredient.getText().toString();
//                if(Ingredient.equals("")){
//                    Toast.makeText(getApplication(), "Write Ingredient!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String category = mCategory;
//                if(category.equals("")){
//                    Toast.makeText(getApplication(), "Choose Category_Country!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String category2 = mCategory2;
//                if(category2.equals("")){
//                    Toast.makeText(getApplication(), "Choose Category_Cooking!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                if(recipes.size() == 0){
//                    Toast.makeText(getApplication(), "Write Recipe!!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//                MyApplication myapp = (MyApplication) getApplication();
//                JsonArray jsonarray = new JsonArray();
//
//                for(int i=0; i<recipes.size(); i++){
//                    JsonObject inter = new JsonObject();
//                    try{
//                        inter.addProperty("index", (i+1)+"");
//                        inter.addProperty("descript", recipes.get(i));
//                        jsonarray.add(inter);
//                    }catch(JsonIOException e){
//                        e.printStackTrace();
//                    }
//                }
//
//                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
//                        .baseUrl(HttpInterface.BaseURL)
//                        .build();
//                HttpInterface httpInterface = retrofit.create(HttpInterface.class);
//
//                AccessToken a = AccessToken.getCurrentAccessToken();
//
//                Call<JsonObject> editPage = httpInterface.editPage(Keyword, Ingredient , a.getUserId(), category, category2 ,jsonarray.toString());
//                editPage.enqueue(new Callback<JsonObject>() {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                        finish();
//                        }
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t) {
//                        Toast.makeText(getApplication(), "FAILURE", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });


//    public void setRecipeAdpater(Context context, Activity activity) {
//        mAdapter = new ListViewAdapter(recipes, context);
//        mListView.setAdapter(mAdapter);
//        return;
//    }

