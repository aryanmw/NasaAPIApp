package com.wadhavekar.nasainfo.UIclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wadhavekar.nasainfo.R;


public class SplashScreen extends AppCompatActivity {

    private static int splashTimeOut = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,APOD.class);
                startActivity(intent);
                finish();
            }
        },splashTimeOut);
    }
}