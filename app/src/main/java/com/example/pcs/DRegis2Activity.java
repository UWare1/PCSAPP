package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class DRegis2Activity extends AppCompatActivity {

    String[] university = {"Kasetsart University","Mahidol University","Silapakorn University","Thammasat University","Bangkok University","Rangsit University"};
    AutoCompleteTextView autoCompleteUni;
    ArrayAdapter<String> adapterUni;

    TextInputLayout Fullname, Address, RegularHospital;
    ImageView TitleText, Back;
    Button Next, Patient, Doctor;
    String DoctorID, Pincode, University, Email, NationalIDCard, Gender, Date;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dregis2);

        Fullname          = findViewById(R.id.Fullname);
        Address           = findViewById(R.id.Address);
        RegularHospital   = findViewById(R.id.RegularHospital);
        Next              = findViewById(R.id.Next);
        Patient           = findViewById(R.id.Patient);
        Doctor            = findViewById(R.id.Doctor);
        TitleText         = findViewById(R.id.titletext);
        Back              = findViewById(R.id.Back);
        decorView = getWindow().getDecorView();

        DoctorID         = getIntent().getStringExtra("DoctorID");
        Pincode       = getIntent().getStringExtra("Pincode");
        Email          = getIntent().getStringExtra("Email");
        NationalIDCard = getIntent().getStringExtra("NationalIDCard");

        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);


        autoCompleteUni = findViewById(R.id.University);
        adapterUni = new ArrayAdapter<String>(this,R.layout.list_university, university);
        autoCompleteUni.setAdapter(adapterUni);
        autoCompleteUni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                University = parent.getItemAtPosition(position).toString();
            }
        });

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

        if(!validateGender()|!validateAge()|!validateFullName()|!validateAddress()|!validateRegularHospital()){
            return;
        }

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        Gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Date = day + "/" + month + "/" + year;

        Intent call = new Intent(getApplicationContext(), DRegis3Activity.class);

        call.putExtra("DoctorID", DoctorID);
        call.putExtra("Pincode", Pincode);
        call.putExtra("Email", Email);
        call.putExtra("NationalIDCard", NationalIDCard);
        call.putExtra("Fullname", Fullname.getEditText().getText().toString().trim());
        call.putExtra("Address", Address.getEditText().getText().toString().trim());
        call.putExtra("RegularHospital", RegularHospital.getEditText().getText().toString().trim());
        call.putExtra("University", University);
        call.putExtra("Gender", Gender);
        call.putExtra("Date", Date);


        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegis2Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), DRegisActivity.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegis2Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    private boolean validateFullName() {
        String val = Fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Fullname.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else {
            Fullname.setError(null);
            Fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRegularHospital() {
        String val = RegularHospital.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            RegularHospital.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else {
            RegularHospital.setError(null);
            RegularHospital.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = Address.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Address.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else {
            Address.setError(null);
            Address.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, getString(R.string.please_select_gender), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, getString(R.string.you_are_not_eligible_to_apply), Toast.LENGTH_SHORT).show();
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
    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }
}