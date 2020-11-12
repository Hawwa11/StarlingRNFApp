package com.example.android.starlingrnfapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo=findViewById(R.id.logo);

        Animation animation3= AnimationUtils.loadAnimation(this,R.anim.bounce);
        logo.startAnimation(animation3);

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }

                }, SPLASH_TIME_OUT);


    }
}