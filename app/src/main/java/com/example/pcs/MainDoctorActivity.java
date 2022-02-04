package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainDoctorActivity extends AppCompatActivity {

    String Doc, Pin;
    EditText DoctorID, Pincode;
    Button Patient, Doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

        DoctorID  = findViewById(R.id.DoctorID);
        Pincode   = findViewById(R.id.Pincode);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);

        Doc = DoctorID.getText().toString();
        Pin = Pincode.getText().toString();

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
                RestartActivity();
            }
        });
    }
    private void RestartActivity(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
