package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    String User = "", Pass = "", test1 = "TestID", test2 = "TestPassword";
    TextInputLayout UserID, Password;
    TextView FPassword, Register;
    View Facebook, Google, Twitter, Line;
    Button Login, Patient, Doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserID    = findViewById(R.id.UserID);
        Password  = findViewById(R.id.Password);
        FPassword = findViewById(R.id.FPassword);
        Register  = findViewById(R.id.Register);
        Login     = findViewById(R.id.Login);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);
        Facebook  = findViewById(R.id.Facebook);
        Google    = findViewById(R.id.Google);
        Twitter   = findViewById(R.id.Twitter);
        Line      = findViewById(R.id.Line);


        User = UserID.getEditText().toString().trim();
        Pass = Password.getEditText().toString().trim();

        FPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Forgot = new Intent(getApplicationContext(), PFPassActivity.class);
                startActivity(Forgot);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regis = new Intent(getApplicationContext(), PRegisActivity.class);
                startActivity(Regis);
                finish();
            }
        });
        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartActivity();
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Doct = new Intent(getApplicationContext(), MainDoctorActivity.class);
                startActivity(Doct);
                finish();
            }
        });Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Doct = new Intent(getApplicationContext(), MainDoctorActivity.class);
                startActivity(Doct);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Logi = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(Logi);
                finish();
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
