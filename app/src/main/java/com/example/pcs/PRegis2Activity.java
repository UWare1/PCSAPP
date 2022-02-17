package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PRegis2Activity extends AppCompatActivity {

    TextInputLayout Fullname, Address, Medical, Allergy;
    ImageView TitleText, Back;
    Button Next, Patient, Doctor;
    String UserID, Password, Email, NationalIDCard, Gender, Date;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregis2);


        Fullname          = findViewById(R.id.Fullname);
        Address           = findViewById(R.id.Address);
        Medical           = findViewById(R.id.Medical);
        Allergy           = findViewById(R.id.Allergy);
        Next              = findViewById(R.id.Next);
        Patient           = findViewById(R.id.Patient);
        Doctor            = findViewById(R.id.Doctor);
        TitleText         = findViewById(R.id.titletext);
        Back              = findViewById(R.id.Back);
        decorView = getWindow().getDecorView();

        UserID         = getIntent().getStringExtra("UserID");
        Password       = getIntent().getStringExtra("Password");
        Email          = getIntent().getStringExtra("Email");
        NationalIDCard = getIntent().getStringExtra("NationalIDCard");

        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);


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

    public void CallNextRegister(View view) {

        if(!validateGender()|!validateAge()|!validateFullName()|!validateAddress()|!validateMedical()|!validateAllergy() ){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        Gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Date = day + "/" + month + "/" + year;

        Intent call = new Intent(getApplicationContext(), PRegis3Activity.class);

        call.putExtra("UserID", UserID);
        call.putExtra("Password", Password);
        call.putExtra("Email", Email);
        call.putExtra("NationalIDCard", NationalIDCard);
        call.putExtra("Fullname", Fullname.getEditText().getText().toString().trim());
        call.putExtra("Address", Address.getEditText().getText().toString().trim());
        call.putExtra("Medical", Medical.getEditText().getText().toString().trim());
        call.putExtra("Allergy", Allergy.getEditText().getText().toString().trim());
        call.putExtra("Gender", Gender);
        call.putExtra("Date", Date);


        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegis2Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), PRegisActivity.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegis2Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    private boolean validateFullName() {
        String val = Fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Fullname.setError("Field can not be empty");
            return false;
        } else {
            Fullname.setError(null);
            Fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateMedical() {
        String val = Medical.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Medical.setError("Field can not be empty");
            return false;
        } else {
            Medical.setError(null);
            Medical.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateAllergy() {
        String val = Allergy.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Allergy.setError("Field can not be empty");
            return false;
        } else {
            Allergy.setError(null);
            Allergy.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = Address.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Address.setError("Field can not be empty");
            return false;
        } else {
            Address.setError(null);
            Address.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
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