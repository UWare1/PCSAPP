package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PNewPassChange extends AppCompatActivity {

    Button BackMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnew_pass_change);

        BackMenu = findViewById(R.id.BackMenu);
        BackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BM = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(BM);
                finish();
            }
        });
    }
}
