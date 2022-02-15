package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class PRegisActivity extends AppCompatActivity {


    TextInputLayout UserID, Password, ConPass, Email, NationalIDCard;
    ImageView TitleText, Back;
    Button Next, Patient, Doctor;
    String X1, X2, X3, X4;
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

        /*User        = UserID.getEditText().getText().toString().trim();
        Pass        = Password.getEditText().getText().toString().trim();
        ConP        = ConPass.getEditText().getText().toString().trim();
        Emai        = Email.getEditText().getText().toString().trim();
        NationalID  = NationalIDCard.getEditText().getText().toString().trim();*/
        /*X1 = getIntent().getStringExtra("X1");
        X2 = getIntent().getStringExtra("X2");
        X3 = getIntent().getStringExtra("X3");
        X4 = getIntent().getStringExtra("X4");

        String x1 = "Isas", x2 = "Isas";

        int x = 1;
        if(!X1.isEmpty()){
            UserID.getEditText().setText(X1);
        } else if(!X2.isEmpty()){
            Password.getEditText().setText(X2);
            ConPass.getEditText().setText(X2);
        } else if(!X3.isEmpty()){
            Email.getEditText().setText(X3);
        } else if(!X4.isEmpty()){
            NationalIDCard.getEditText().setText(X4);
        } else {
            UserID.getEditText().setText("");
            Password.getEditText().setText("");
            ConPass.getEditText().setText("");
            Email.getEditText().setText("");
            NationalIDCard.getEditText().setText("");
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

        if (!validateUserID() | !validatePassword() | !validateConfirmPassword() | !validateEmail() | !validateNationalIDCard()) {
            return;
        }

        Intent call = new Intent(getApplicationContext(), PRegis2Activity.class);

        call.putExtra("UserID", UserID.getEditText().getText().toString().trim());
        call.putExtra("Password", Password.getEditText().getText().toString().trim());
        call.putExtra("Email", Email.getEditText().getText().toString().trim());
        call.putExtra("NationalIDCard", NationalIDCard.getEditText().getText().toString().trim());

        String a = ConPass.getEditText().getText().toString().trim();
        String b = Password.getEditText().getText().toString().trim();
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (a.equals(b)) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegisActivity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            Toast.makeText(this, "Password Mismatch!", Toast.LENGTH_SHORT).show();
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
        String checkspaces = "\\A\\w{1,20}\\z";
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
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        Pattern special = Pattern.compile("[!/@#$<>?}{)(%^&+=*+-]");
        if (val.isEmpty()) {
            Password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            Password.setError("Password should contain 4 characters!");
            return false;
        } else if (val.contains(" ")) {
            Password.setError("Password should not space");
            return false;
        } else if (special.matcher(val).find()) {
            Password.setError("Password should have Alphabet or Number");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateConfirmPassword() {
        String val = ConPass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        Pattern special = Pattern.compile("[!/@#$<>?}{)(%^&+=*+-]");
        if (val.isEmpty()) {
            ConPass.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            ConPass.setError("Password should contain 4 characters!");
            return false;
        } else if (val.contains(" ")) {
            ConPass.setError("Password should not space");
            return false;
        } else if (special.matcher(val).find()) {
            ConPass.setError("Password should have Alphabet or Number");
            return false;
        } else {
            ConPass.setError(null);
            ConPass.setErrorEnabled(false);
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
        } else if (val.length() > 13) {
            NationalIDCard.setError("National ID Card is too large!");
            return false;
        } else if (val.length() < 13) {
            NationalIDCard.setError("National ID Card is too short!");
            return false;
        } else {
            NationalIDCard.setError(null);
            NationalIDCard.setErrorEnabled(false);
            return true;
        }
    }
}
