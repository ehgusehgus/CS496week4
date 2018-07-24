package com.example.q.cs496_week4.DetailSearchActivity;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Model2 {
        public static final int SEARCH_KEYWORD_TYPE = 0;
        public static final int SEARCH_RECIPE_TYPE = 1;
        public static final int SEARCH_IMAGE_TYPE = 2;

        public int type;
        public String text;
        public String text2;
        public ArrayList<String> strings;
        public String bitmap;

        public Model2(int type, String text, String text2, ArrayList<String> strings, String bitmap)
        {
            this.type=type;
            this.text=text;
            this.text2=text2;
            this.strings = strings;
            this.bitmap = bitmap;
        }
}
