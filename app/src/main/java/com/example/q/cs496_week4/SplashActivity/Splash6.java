package com.example.q.cs496_week4.SplashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.q.cs496_week4.MainActivity;
import com.example.q.cs496_week4.R;

public class Splash6 extends Activity {

    /** 로딩 화면이 떠있는 시간(밀리초단위)  **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash6);

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                Intent mainIntent = new Intent(Splash6.this,MainActivity.class);
                Splash6.this.startActivity(mainIntent);
                Splash6.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
