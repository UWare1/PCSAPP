package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    LinearLayout Splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Splash = findViewById(R.id.Splash);
        AnimatorSet Fade = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.fade);
        Fade.setTarget(Splash);
        Fade.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LanguagePage.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }
}