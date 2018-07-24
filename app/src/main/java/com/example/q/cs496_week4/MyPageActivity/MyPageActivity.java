package com.example.q.cs496_week4.MyPageActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.cs496_week4.MyApplication;
import com.example.q.cs496_week4.MyPageActivity.myrecipe.MyRecipe;
import com.example.q.cs496_week4.R;
import com.example.q.cs496_week4.UserActivity.LoginActivity;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.io.InputStream;

public class MyPageActivity extends AppCompatActivity{

    public static String nickname;
    private TextView nickview;

    private Button mymenu;
    private Button editinfo;
    private Button option;
    private Button logout;
    @RequiresApi(api = 28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        nickname = MyApplication.getNickname();
        nickview = (TextView) findViewById(R.id.NickName);
        nickview.setText(nickname);

        Profile profile = Profile.getCurrentProfile();
        String imageUrl = profile.getProfilePictureUri(80,80).toString();
        new MyPageActivity.DownloadImage((ImageView)findViewById(R.id.ProfileImage)).execute(imageUrl);

        mymenu = (Button) findViewById(R.id.MyRecipe);
        mymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myrecipe = new Intent(MyPageActivity.this, MyRecipe.class);
                //id정보 추가
                startActivity(myrecipe);

            }
        });


        option = (Button) findViewById(R.id.Option);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent option = new Intent(MyPageActivity.this, Help.class);

                startActivity(option);

            }
        });

        logout = (Button) findViewById(R.id.LogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(MyPageActivity.this).create();
                alertDialog.setTitle("LOGOUT");
                alertDialog.setMessage("Do you really want to log out?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginManager.getInstance().logOut();
                        Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialog.show();
            }
        });
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try{
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }

    }

}
