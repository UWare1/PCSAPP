package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class PNewPassChange extends AppCompatActivity {

    LinearLayout Lay;
    Button BackMenu;
    Animation FadeInUp;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnew_pass_change);

        Lay      = findViewById(R.id.lay2);
        BackMenu = findViewById(R.id.BackMenu);
        decorView = getWindow().getDecorView();

        FadeInUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeinup);
        Lay.startAnimation(FadeInUp);
        BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BM = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(BM);
                finish();
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}
