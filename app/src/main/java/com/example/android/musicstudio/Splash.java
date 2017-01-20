package com.example.android.musicstudio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StartAnimations();
        sharedPreferences = this.getSharedPreferences("MyPrefs", this.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (sharedPreferences.getBoolean("login", false)) {
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.tampillogo);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splashscreen);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

}
