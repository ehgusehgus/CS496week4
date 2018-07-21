package com.example.q.cs496_week4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EditActivity extends AppCompatActivity {

    String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
                                //선택 시 스트링 내의 선택사항의 포지션을 지정
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




    }
}
