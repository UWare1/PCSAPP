package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class PRegisActivity extends AppCompatActivity {


    TextInputLayout UserID, Password, ConPass, Email, NationalIDCard;
    ImageView TitleText, Back;
    Button Next, Patient, Doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregis);

        UserID            = findViewById(R.id.UserID);
        Password          = findViewById(R.id.Password);
        ConPass           = findViewById(R.id.ConPass);
        Email             = findViewById(R.id.Email);
        NationalIDCard    = findViewById(R.id.NationalIDCard);
        Next              = findViewById(R.id.Next);
        Patient           = findViewById(R.id.Patient);
        Doctor            = findViewById(R.id.Doctor);
        TitleText         = findViewById(R.id.titletext);
        Back              = findViewById(R.id.Back);

        /*int x = 1;
        if(x==1){
            UserID.getEditText().setText("Yutthana");
        } else {

        }*/



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
    public void CallNextRegister(View view){

        if (!validateUserID() | !validatePassword() | !validateEmail() | !validateNationalIDCard()) {
            return;
        }

        Intent call = new Intent(getApplicationContext(), PRegis2Activity.class);

        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegisActivity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), MainActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[2] = new Pair<View, String>(Doctor, "transition_doctor_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegisActivity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }

    }
    private boolean validateUserID() {
        String val = UserID.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            UserID.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            UserID.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            UserID.setError("No White spaces are allowed!");
            return false;
        } else {
            UserID.setError(null);
            UserID.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = Password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            Password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            Password.setError("Password should contain 4 characters!");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }



    private boolean validateEmail() {
        String val = Email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            Email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            Email.setError("Invalid Email!");
            return false;
        } else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNationalIDCard() {
        String val = NationalIDCard.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            NationalIDCard.setError("Field can not be empty");
            return false;
        } else if (val.length() > 14) {
            NationalIDCard.setError("National ID Card is too large!");
            return false;
        } else {
            NationalIDCard.setError(null);
            NationalIDCard.setErrorEnabled(false);
            return true;
        }
    }
}
