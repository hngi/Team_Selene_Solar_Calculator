package com.example.solarcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
 * Created by Utibe Etim (@Ut_et)
 * */


public class SplashScreenActivity extends AppCompatActivity {
    TextView mBoldText;
    ImageView mlightening;
    Animation frombottom;
    Animation fromtop;
    TextView mSmallText;
    RelativeLayout mConstraints;
    ImageView mBold_lightening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mConstraints = findViewById(R.id.constraints);
        mlightening = findViewById(R.id.lightening);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        mConstraints.setAnimation(frombottom);
        mlightening.setAnimation(fromtop);

        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override

            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
                finish();        // close this activity
            }

        },  4000); // wait for 3 seconds
    }
}
