package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PSetNewPassword extends AppCompatActivity {


    TextInputLayout Password, ConPass;
    String UserID , _phoneNumber, _newPassword;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pset_new_password);

        Password = findViewById(R.id.Password);
        ConPass = findViewById(R.id.ConPass);
        decorView = getWindow().getDecorView();
    }


    public void setNewPasswordBtn(View view) {

        if (!validatePassword() | !validateConfirmPassword()) {
            return;
        }
        _newPassword = Password.getEditText().getText().toString().trim();
        _phoneNumber = getIntent().getStringExtra("phoneNo");
        UserID = getIntent().getStringExtra("UserID");


        if (_newPassword.equals(ConPass.getEditText().getText().toString().trim())) {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher");
            reference.child(UserID).child("password").setValue(_newPassword);

            startActivity(new Intent(getApplicationContext(), PNewPassChange.class));
            finish();
        } else {
            Toast.makeText(this, getString(R.string.passwprd_not_match), Toast.LENGTH_SHORT).show();
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
            Password.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (!val.matches(checkPassword)) {
            Password.setError(getString(R.string.password_should_contain_4_characters));
            return false;
        } else if (val.contains(" ")) {
            Password.setError(getString(R.string.password_should_not_space));
            return false;
        } else if (special.matcher(val).find()) {
            Password.setError(getString(R.string.password_should_have_alphabet_or_number));
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
            ConPass.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (!val.matches(checkPassword)) {
            ConPass.setError(getString(R.string.password_should_contain_4_characters));
            return false;
        } else if (val.contains(" ")) {
            ConPass.setError(getString(R.string.password_should_not_space));
            return false;
        } else if (special.matcher(val).find()) {
            ConPass.setError(getString(R.string.password_should_have_alphabet_or_number));
            return false;
        } else {
            ConPass.setError(null);
            ConPass.setErrorEnabled(false);
            return true;
        }
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