package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PFPassActivity extends AppCompatActivity {

    EditText RecoverEmail;
    Button Recover, Patient, Doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfpass);

        RecoverEmail = findViewById(R.id.RecoverEmail);
        Recover      = findViewById(R.id.Recover);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);

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
    }
}

