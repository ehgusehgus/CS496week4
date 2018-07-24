package com.example.q.cs496_week4.DetailEditActivity;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Model {
    public static final int EDIT_KEYWORD_TYPE=0;
    public static final int EDIT_CATEGORY_TYPE=1;
    public static final int EDIT_CATEGORY2_TYPE=2;
    public static final int EDIT_INGREDIENT_TYPE=3;
    public static final int EDIT_TAG_TYPE=4;
    public static final int EDIT_LISTVIEW_TYPE=5;
    public static final int EDIT_RECIPE_TYPE = 6;
    public static final int EDIT_IMAGE_TYPE = 7;

    public int type;
    public String text;
    public String text2;
    public ArrayList<String> strings;
    public Bitmap bitmap;

    public Model(int type, String text, String text2, ArrayList<String> strings, Bitmap bitmap)
    {
        this.type=type;
        this.text=text;
        this.text2=text2;
        this.strings = strings;
        this.bitmap = bitmap;
    }
}
