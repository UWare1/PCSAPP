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


    TextInputLayout PhoneNumber, Password, ConPass;
    String UserID , _phoneNumber, _newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pset_new_password);
        Password = findViewById(R.id.Password);
        ConPass = findViewById(R.id.ConPass);
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
            Toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show();
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


}