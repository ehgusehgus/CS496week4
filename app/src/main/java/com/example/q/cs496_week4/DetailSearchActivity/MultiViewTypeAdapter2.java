package com.example.q.cs496_week4.DetailSearchActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import com.example.q.cs496_week4.DetailEditActivity.Model;
import com.example.q.cs496_week4.HttpInterface;
import com.example.q.cs496_week4.R;
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

/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter2 extends RecyclerView.Adapter {

    private ArrayList<Model2>dataSet;
    Context mContext;
    int total_types;

    private ListView mListView;
    private TextView mTextView;

    TextView mRecipe;

    ArrayList<String> recipes = new ArrayList<String>();

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txtType;
        TextView txtType2;
        CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.type);
            this.txtType2 = (TextView) itemView.findViewById(R.id.type2);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
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

    public MultiViewTypeAdapter2(ArrayList<Model2>data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Model.EDIT_KEYWORD_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
                return new TextTypeViewHolder(view);
            case Model.EDIT_RECIPE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reciepe_item, parent, false);
                return new RecipeTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Model.EDIT_KEYWORD_TYPE;
            case 1:
                return Model.EDIT_RECIPE_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Model2 object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case Model.EDIT_KEYWORD_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).txtType2.setText(object.text2);
                    break;

                case Model.EDIT_RECIPE_TYPE:
                    ((RecipeTypeViewHolder) holder).txtType.setText(object.text);
                    ((RecipeTypeViewHolder) holder).txtType2.setText(object.text2);
                    break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
