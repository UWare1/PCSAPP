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

public class DSetNewPincode extends AppCompatActivity {

    TextInputLayout Pincode, ConPass;
    String DoctorID , _phoneNumber, _newPincode;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dset_new_pincode);

        Pincode = findViewById(R.id.Pincode);
        ConPass = findViewById(R.id.ConPass);
        decorView = getWindow().getDecorView();
    }


    public void setNewPasswordBtn(View view) {

        if (!validatePassword() | !validateConfirmPassword()) {
            return;
        }
        _newPincode = Pincode.getEditText().getText().toString().trim();
        _phoneNumber = getIntent().getStringExtra("phoneNo");
        DoctorID = getIntent().getStringExtra("DoctorID");


        if (_newPincode.equals(ConPass.getEditText().getText().toString().trim())) {
            DatabaseReference reference = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor");
            reference.child(DoctorID).child("pincode").setValue(_newPincode);

            startActivity(new Intent(getApplicationContext(), DNewPinChange.class));
            finish();
        } else {
            Toast.makeText(this, "Password not match!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatePassword() {
        String val = Pincode.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        Pattern special = Pattern.compile("[!/@#$<>?}{)(%^&+=*+-]");
        if (val.isEmpty()) {
            Pincode.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            Pincode.setError("Password should contain 4 characters!");
            return false;
        } else if (val.contains(" ")) {
            Pincode.setError("Password should not space");
            return false;
        } else if (special.matcher(val).find()) {
            Pincode.setError("Password should have Alphabet or Number");
            return false;
        } else {
            Pincode.setError(null);
            Pincode.setErrorEnabled(false);
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