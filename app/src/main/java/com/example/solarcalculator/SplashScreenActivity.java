package com.example.solarcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    TextView sp_tv;
    ImageView baloon;
    Animation frombottom;
    Animation fromtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        sp_tv = (TextView) findViewById(R.id.sp_tv);
//        baloon = (ImageView) findViewById(R.id.baloon);
//        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
//        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
//        sp_tv.setAnimation(frombottom);
//        baloon.setAnimation(fromtop);

        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override

            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();        // close this activity
            }

        }, 3 * 1000); // wait for 3 seconds
    }
}
