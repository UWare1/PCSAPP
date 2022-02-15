package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class PFPassActivity extends AppCompatActivity {

    ImageView TitleText;
    TextInputLayout RecoverEmail;
    Button Recover, Patient, Doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfpass);

        RecoverEmail = findViewById(R.id.RecoverEmail);
        Recover      = findViewById(R.id.Recover);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);
        TitleText         = findViewById(R.id.titletext);

        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Pat = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Pat);
                finish();
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Doct = new Intent(getApplicationContext(), MainDoctorActivity.class);
                startActivity(Doct);
                finish();
            }
        });
        Recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Rec = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Rec);
                finish();
            }
        });
    }
    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), MainActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[2] = new Pair<View, String>(Doctor, "transition_doctor_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PFPassActivity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
}

