package com.example.q.cs496_week4.DetailEditActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
import com.facebook.AccessToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private ArrayList<Model>dataSet;
    Context mContext;
    int total_types;
    private ListView mListView;
    private TextView mTextView;
    ArrayList<Bitmap> recipe_image = new ArrayList<Bitmap>();
    Boolean is_first;
    TextTypeViewHolder mKeyWord;

    ButtonTypeViewHolder mCategory;
    ButtonTypeViewHolder mCategory2;
    TextTypeViewHolder mIngredient;
    ImageTypeViewHolder mRepImage;
    TextView mRecipe;
    TextTypeViewHolder mTag;

    ArrayList<String> recipes = new ArrayList<String>();

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        TextView txtType2;
        CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.txtType2 = (EditText) itemView.findViewById(R.id.type2);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

    public static class ListviewTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        TextView txtType2;
        Button add_btn;
        Button finish_btn;
        TextView txtType3;

        public ListviewTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.txtType2 = (EditText) itemView.findViewById(R.id.textView12);
            this.add_btn = (Button) itemView.findViewById(R.id.button6);
            this.finish_btn = (Button) itemView.findViewById(R.id.button7);
            this.txtType3 = (TextView) itemView.findViewById(R.id.textView14);
        }
    }

    public static class ButtonTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        Button btn;

        public ButtonTypeViewHolder(View itemView) {
            super(itemView);

            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.btn = (Button) itemView.findViewById(R.id.button4);
        }
    }
    public static class RecipeTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        TextView txtType2;
        ImageView image;

        public RecipeTypeViewHolder(View itemView) {
            super(itemView);
            Log.d("????", txtType +"   " + txtType2 );
            this.txtType = (TextView) itemView.findViewById(R.id.index);
            this.txtType2 = (TextView) itemView.findViewById(R.id.content);
            this.image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        ImageView image;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.image = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }


    public MultiViewTypeAdapter(ArrayList<Model>data, Context context, boolean is_first) {
        this.dataSet = data;
        this.mContext = context;
        this.is_first = is_first;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Model.EDIT_KEYWORD_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                mKeyWord = new TextTypeViewHolder(view);
                return mKeyWord;
            case Model.EDIT_CATEGORY_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_type, parent, false);
                mCategory =new ButtonTypeViewHolder(view);
                return mCategory;
            case Model.EDIT_CATEGORY2_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_type, parent, false);
                mCategory2 = new ButtonTypeViewHolder(view);
                return mCategory2;
            case Model.EDIT_INGREDIENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                mIngredient = new TextTypeViewHolder(view);
                return mIngredient;
            case Model.EDIT_TAG_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                mTag = new TextTypeViewHolder(view);
                return mTag;
            case Model.EDIT_LISTVIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_type, parent, false);
                return new ListviewTypeViewHolder(view);
            case Model.EDIT_RECIPE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciepe_item, parent, false);
                return new RecipeTypeViewHolder(view);
            case Model.EDIT_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
                mRepImage = new ImageTypeViewHolder(view);
                return mRepImage;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.EDIT_KEYWORD_TYPE;
            case 1:
                return Model.EDIT_CATEGORY_TYPE;
            case 2:
                return Model.EDIT_CATEGORY2_TYPE;
            case 3:
                return Model.EDIT_INGREDIENT_TYPE;
            case 4:
                return Model.EDIT_TAG_TYPE;
            case 5:
                return Model.EDIT_LISTVIEW_TYPE;
            case 6:
                return Model.EDIT_RECIPE_TYPE;
            case 7:
                return Model.EDIT_IMAGE_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Model object = dataSet.get(listPosition);
        Log.d("???????", listPosition+"");
        Log.d("???????", holder+"");
        if (object != null) {
            switch (object.type) {
                case Model.EDIT_KEYWORD_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).txtType2.setText(object.text2);
                    break;
                case Model.EDIT_CATEGORY_TYPE:
                    ((ButtonTypeViewHolder) holder).txtType.setText(object.text);
                    if(!object.text2.equals(""))
                        ((ButtonTypeViewHolder)holder).btn.setText(object.text2);

                    ((ButtonTypeViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String[] items = new String[]{"KOR", "WEST", "CHN", "JPN", "ETC"};
                            final int[] selectedIndex = {0};

                            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
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
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[0]);
                                                //mCategory = items[0];
                                            }
                                            if(selectedIndex[0] == 1){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[1]);
                                                //mCategory = items[1];
                                            }
                                            if(selectedIndex[0] == 2){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[2]);
                                                //mCategory = items[2];
                                            }
                                            if(selectedIndex[0] == 3){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[3]);
                                                //mCategory = items[3];
                                            }
                                            if(selectedIndex[0] == 4){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[4]);
                                                //mCategory = items[4];
                                            }
                                        }
                                    }).create().show();
                        }
                    });
                    break;
                case Model.EDIT_CATEGORY2_TYPE:
                    ((ButtonTypeViewHolder) holder).txtType.setText(object.text);
                    if(!object.text2.equals(""))
                        ((ButtonTypeViewHolder)holder).btn.setText(object.text2);
                    ((ButtonTypeViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String[] items = new String[]{"밥", "면", "국물", "찜/조림", "구이","볶음","튀김/부침","나물","디저트","기타"};
                            final int[] selectedIndex = {0};

                            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
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
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[0]);
                                                //mCategory2 = items[0];
                                            }
                                            if(selectedIndex[0] == 1){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[1]);
                                                //mCategory2 = items[1];
                                            }
                                            if(selectedIndex[0] == 2){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[2]);
                                                //mCategory2 = items[2];
                                            }
                                            if(selectedIndex[0] == 3){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[3]);
                                                //mCategory2 = items[3];
                                            }
                                            if(selectedIndex[0] == 4){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[4]);
                                                //mCategory2 = items[4];
                                            }
                                            if(selectedIndex[0] == 5){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[5]);
                                                //mCategory2 = items[5];
                                            }
                                            if(selectedIndex[0] == 6){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[6]);
                                                //mCategory2 = items[6];
                                            }
                                            if(selectedIndex[0] == 7){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[7]);
                                                //mCategory2 = items[7];
                                            }
                                            if(selectedIndex[0] == 8){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[8]);
                                                //mCategory2 = items[8];
                                            }
                                            if(selectedIndex[0] == 9){
                                                ((ButtonTypeViewHolder) holder).btn.setText(items[9]);
                                                //mCategory2 = items[9];
                                            }
                                        }
                                    }).create().show();
                        }
                    });
                    break;
                case Model.EDIT_INGREDIENT_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).txtType2.setText(object.text2);
                    break;
                case Model.EDIT_TAG_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).txtType2.setText(object.text2);
                    break;
                case Model.EDIT_LISTVIEW_TYPE:
                    ((ListviewTypeViewHolder) holder).txtType.setText(object.text);
                    recipes = object.strings;
                    for(int k=0;k<recipes.size();k++)
                        recipe_image.add(null);
//                    mListView = ((ListviewTypeViewHolder) holder).listView;
//                    setRecipeAdpater();
                    ((ListviewTypeViewHolder) holder).txtType2.setText(object.text2);

                    ((ListviewTypeViewHolder) holder).add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add recipe and reset adapter
                        String Recipedesc = ((ListviewTypeViewHolder) holder).txtType2.getText().toString();
                        if(Recipedesc.equals("")){
                            Toast.makeText(mContext.getApplicationContext(), "Write Description!!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        recipes.add(Recipedesc);
                        recipe_image.add(null);
                        setRecipeAdpater(recipes);

                        }
                    });

                    ((ListviewTypeViewHolder) holder).finish_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Keyword = mKeyWord.txtType2.getText().toString();
                        if(Keyword.equals("")){
                            Toast.makeText(mContext.getApplicationContext(), "Write KeyWord!!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String Ingredient = mIngredient.txtType2.getText().toString();
                        if(Ingredient.equals("")){
                            Toast.makeText(mContext.getApplicationContext(), "Write Ingredient!!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String category = mCategory.btn.getText().toString();
                        if(category.equals("Select")){
                            Toast.makeText(mContext.getApplicationContext(), "Choose Category_Country!!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String category2 = mCategory2.btn.getText().toString();
                        if(category2.equals("Select")){
                            Toast.makeText(mContext.getApplicationContext(), "Choose Category_Cooking!!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String tag= mTag.txtType2.getText().toString();
                        if(tag.equals("")){
                            Toast.makeText(mContext.getApplicationContext(), "Write tag!!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(tag.charAt(0) != '$')
                        {
                            Toast.makeText(mContext.getApplicationContext(), "Write tag!!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if(recipes.size() == 0){
                            Toast.makeText(mContext.getApplicationContext(), "Write Recipe!!", Toast.LENGTH_LONG).show();
                            return;
                        }
//
//                        Bitmap basic = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.empty);;

                        String b;
                        if(mRepImage.image == null)
                            b="";
                        else{
                            Bitmap bm =((BitmapDrawable)mRepImage.image.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                            byte [] b2=baos.toByteArray();
                            b = Base64.encodeToString(b2, Base64.DEFAULT);
                            Log.d("???", b);
                        }
                        JsonArray jsonarray = new JsonArray();

                        for(int i=0; i<recipes.size(); i++){
                            JsonObject inter = new JsonObject();
                            try{
                                String b4;
                                if(recipe_image.get(i)== null)
                                    b4="";
                                else{
                                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                                    recipe_image.get(i).compress(Bitmap.CompressFormat.JPEG, 100, baos2); //bm is the bitmap object
                                    byte [] b3=baos2.toByteArray();
                                    b4 = Base64.encodeToString(b3, Base64.DEFAULT);
                                }
                                inter.addProperty("index", (i+1)+"");
                                inter.addProperty("descript", recipes.get(i));
                                inter.addProperty("image",b4);
                                jsonarray.add(inter);
                            }catch(JsonIOException e){
                                e.printStackTrace();
                            }
                        }

                        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(HttpInterface.BaseURL)
                                .build();
                        HttpInterface httpInterface = retrofit.create(HttpInterface.class);

                        AccessToken a = AccessToken.getCurrentAccessToken();
                        Log.d(">>>", is_first.toString());
                        if(is_first) {
                            Call<JsonObject> addPage = httpInterface.addPage(Keyword, Ingredient, a.getUserId(), category, category2, tag, jsonarray.toString(), b);
                            addPage.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    ((Activity) mContext).finish();
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(mContext.getApplicationContext(), "FAILURE", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else{
                            Call<JsonObject> editPage = httpInterface.editPage(Keyword, Ingredient, a.getUserId(), category, category2, tag, jsonarray.toString(), b);
                            editPage.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    ((Activity) mContext).finish();
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(mContext.getApplicationContext(), "FAILURE", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });

                    break;

                case Model.EDIT_RECIPE_TYPE:
                    Log.d("????", ((RecipeTypeViewHolder) holder).txtType +"   " + ((RecipeTypeViewHolder) holder).txtType2 );
                    ((RecipeTypeViewHolder) holder).txtType.setText(object.text);
                    ((RecipeTypeViewHolder) holder).txtType2.setText(object.text2);
                    final int position = Integer.parseInt(object.text)-1;

                    if(object.bitmap != null) {
                        ((RecipeTypeViewHolder) holder).image.setImageBitmap(object.bitmap);
                    }
                    else{
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);
                            URL url = new URL(HttpInterface.BaseURL+"images/"+mKeyWord.txtType2.getText().toString()+(position)+".jpg");
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            recipe_image.set(position, image);
                            ((ImageTypeViewHolder) holder).image.setImageBitmap(image);
                        } catch(IOException e) {
                            System.out.println(e);
                        }

//                        Glide.with(mContext)
//                                .load(HttpInterface.BaseURL+"images/"+mKeyWord.txtType2.getText().toString()+(position) +".jpg")
//                                .asBitmap()
//                                .placeholder(R.drawable.empty)
//                                .error(R.drawable.empty)        //Error상황에서 보여진다.
//                                .into(((RecipeTypeViewHolder) holder).image);
//
//                        Bitmap bm =((BitmapDrawable)((RecipeTypeViewHolder) holder).image.getDrawable()).getBitmap();
//                        recipe_image.set(position, bm);
                    }


                    ((RecipeTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(mContext.getApplicationContext());
                            ((Activity) mContext).startActivityForResult(chooseImageIntent, 600+position);
                        }
                    });


                    mTextView = ((RecipeTypeViewHolder) holder).txtType2;
                    mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
//                            final int mposition = pos;
                            // get prompts.xml view
                            LayoutInflater li = LayoutInflater.from(mContext);
                            View promptsView = li.inflate(R.layout.prompts, null);

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    mContext);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);

                            final EditText userInput = (EditText) promptsView
                                    .findViewById(R.id.editTextDialogUserInput);


                            // set dialog message
                            alertDialogBuilder
                                    .setCancelable(true)
                                    .setPositiveButton("Edit",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {
                                                    // get user input and set it to
                                                    // result
                                                    // edit text
                                                    if (userInput.getText().toString().equals("")) {
                                                        Toast.makeText(mContext.getApplicationContext(), "Write Description!!", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }
                                                    recipes.set(position, userInput.getText().toString());
                                                    setRecipeAdpater(recipes);
                                                }
                                            })
                                    .setNegativeButton("DELETE",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int id) {

                                                    recipes.remove(position);
                                                    recipe_image.remove(position);
                                                    setRecipeAdpater(recipes);
                                                    dialog.cancel();
                                                }
                                            });

                            alertDialogBuilder.setNeutralButton("INSERT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int id) {
                                            // get user input and set it to
                                            // result
                                            // edit text
                                            if (userInput.getText().toString().equals("")) {
                                                Toast.makeText(mContext.getApplicationContext(), "Write Description!!", Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                            recipes.add(position + 1, userInput.getText().toString());
                                            recipe_image.add(position+1,null);
                                            setRecipeAdpater(recipes);
                                        }
                                    });


                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();


                            return true;
                        }
                    });
                    break;
                case Model.EDIT_IMAGE_TYPE:
                    ((ImageTypeViewHolder) holder).txtType.setText(object.text);
                    if(object.bitmap != null) {
                        ((ImageTypeViewHolder) holder).image.setImageBitmap(object.bitmap);
                    }
                    else{
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);
                            URL url = new URL(HttpInterface.BaseURL+"images/"+mKeyWord.txtType2.getText().toString()+".jpg");
                            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            ((ImageTypeViewHolder) holder).image.setImageBitmap(image);
                        } catch(IOException e) {
                            System.out.println(e);
                        }
//                        Glide.with(mContext)
//                                .load(HttpInterface.BaseURL+"images/"+mKeyWord.txtType2.getText().toString()+".jpg")
//                                .asBitmap()
//                                .placeholder(R.drawable.empty)
//                                .error(R.drawable.empty)        //Error상황에서 보여진다.
//                                .into(((ImageTypeViewHolder) holder).image);

                    }
                    ((ImageTypeViewHolder) holder).image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent chooseImageIntent = ImagePicker.getPickImageIntent(mContext.getApplicationContext());
                            ((Activity) mContext).startActivityForResult(chooseImageIntent, 234);
                        }
                    });
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setRecipeAdpater(ArrayList<String> insert) {
//        for(int i=0;i<remove;i++)
//            dataSet.remove(dataSet.size()-1);
        ArrayList<Model> list= new ArrayList();

        list.add(new Model(Model.EDIT_KEYWORD_TYPE,"KEYWORD",mKeyWord.txtType2.getText().toString(),insert, null));
        list.add(new Model(Model.EDIT_CATEGORY_TYPE,"CATEGORY_COUNTRY",mCategory.btn.getText().toString(),insert, null));
        list.add(new Model(Model.EDIT_CATEGORY2_TYPE,"CATEGORY_COOKING",mCategory2.btn.getText().toString(),insert, null));
        list.add(new Model(Model.EDIT_INGREDIENT_TYPE,"INGREDIENT",mIngredient.txtType2.getText().toString(),insert, null));
        list.add(new Model(Model.EDIT_TAG_TYPE,"TAG",mTag.txtType2.getText().toString(),insert, null));
        list.add(new Model(Model.EDIT_IMAGE_TYPE, "REPRESENTATIVE IMAGE", "", insert, ((BitmapDrawable)mRepImage.image.getDrawable()).getBitmap()));
        list.add(new Model(Model.EDIT_LISTVIEW_TYPE,"RECIPE","",insert, null));
        for(int i=0;i<insert.size();i++)
            list.add(new Model(Model.EDIT_RECIPE_TYPE,(i+1)+"",recipes.get(i),insert, recipe_image.get(i)));
        dataSet = list;
        notifyDataSetChanged();
        return;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 234:
                Bitmap bitmap = ImagePicker.getImageFromResult(mContext, resultCode, data);
                ArrayList<Model> list= new ArrayList();
                Log.d("????????????",bitmap.toString());

                list.add(new Model(Model.EDIT_KEYWORD_TYPE,"KEYWORD",mKeyWord.txtType2.getText().toString(),recipes, null));
                list.add(new Model(Model.EDIT_CATEGORY_TYPE,"CATEGORY_COUNTRY",mCategory.btn.getText().toString(),recipes, null));
                list.add(new Model(Model.EDIT_CATEGORY2_TYPE,"CATEGORY_COOKING",mCategory2.btn.getText().toString(),recipes, null));
                list.add(new Model(Model.EDIT_INGREDIENT_TYPE,"INGREDIENT",mIngredient.txtType2.getText().toString(),recipes, null));
                list.add(new Model(Model.EDIT_TAG_TYPE,"TAG",mTag.txtType2.getText().toString(),recipes, null));
                list.add(new Model(Model.EDIT_IMAGE_TYPE, "REPRESENTATIVE IMAGE", "", recipes, bitmap));
                list.add(new Model(Model.EDIT_LISTVIEW_TYPE,"RECIPE","",recipes, null));
                for(int i=0;i<recipes.size();i++)
                    list.add(new Model(Model.EDIT_RECIPE_TYPE,(i+1)+"",recipes.get(i),recipes, null));
                dataSet = list;
                notifyDataSetChanged();
                break;
            default:
                break;
        }
        if(requestCode>=600){
            int position = requestCode - 600;

            Bitmap bitmap2 = ImagePicker.getImageFromResult(mContext, resultCode, data);
            ArrayList<Model> list2= new ArrayList();
            recipe_image.set(position, bitmap2);
            list2.add(new Model(Model.EDIT_KEYWORD_TYPE,"KEYWORD",mKeyWord.txtType2.getText().toString(),recipes, null));
            list2.add(new Model(Model.EDIT_CATEGORY_TYPE,"CATEGORY_COUNTRY",mCategory.btn.getText().toString(),recipes, null));
            list2.add(new Model(Model.EDIT_CATEGORY2_TYPE,"CATEGORY_COOKING",mCategory2.btn.getText().toString(),recipes, null));
            list2.add(new Model(Model.EDIT_INGREDIENT_TYPE,"INGREDIENT",mIngredient.txtType2.getText().toString(),recipes, null));
            list2.add(new Model(Model.EDIT_TAG_TYPE,"TAG",mTag.txtType2.getText().toString(),recipes, null));
            list2.add(new Model(Model.EDIT_IMAGE_TYPE, "REPRESENTATIVE IMAGE", "", recipes, ((BitmapDrawable)mRepImage.image.getDrawable()).getBitmap()));
            list2.add(new Model(Model.EDIT_LISTVIEW_TYPE,"RECIPE","",recipes, null));
            for(int i=0;i<recipes.size();i++) {
                list2.add(new Model(Model.EDIT_RECIPE_TYPE, (i + 1) + "", recipes.get(i), recipes, recipe_image.get(i)));
            }
            dataSet = list2;
            notifyDataSetChanged();
        }
    }
}
