package com.example.q.cs496_week4.NoticeBoardActivity;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Model3 {
    public static final int TEXT_TYPE = 0;
    public static final int DIFF_TEXT_TYPE = 1;
    public static final int DIFF_RECIPE_TYPE = 2;

    public int type;
    public String text;
    public String text2;
    public String text3;
    public String bitmap;

    public Model3(int type, String text, String text2, String text3, String bitmap)
    {
        this.type=type;
        this.text=text;
        this.text2=text2;
        this.text3=text3;
        this.bitmap = bitmap;
    }
}