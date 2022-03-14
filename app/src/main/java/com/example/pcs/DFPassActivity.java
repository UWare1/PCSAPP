package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class DFPassActivity extends AppCompatActivity {

    ImageView TitleText;
    TextInputLayout DoctorID, PhoneNumber;
    Button Recover, Patient, Doctor;
    CountryCodePicker CCP;
    String DoctorUser, _phoneNumber;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dfpass);
        DoctorID = findViewById(R.id.DoctorID);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        Recover = findViewById(R.id.Recover);
        Patient = findViewById(R.id.Patient);
        Doctor = findViewById(R.id.Doctor);
        TitleText = findViewById(R.id.titletext);
        CCP = findViewById(R.id.CCP);
        decorView = getWindow().getDecorView();

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

    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), MainDoctorActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[2] = new Pair<View, String>(Doctor, "transition_doctor_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DFPassActivity.this, pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }

    public void VerifyPhoneNumber(View view) {
        //check net
        //validate phone number
        if (!validatePhoneNumber() | !validateUserID()) {
            return;
        }

        DoctorUser = DoctorID.getEditText().getText().toString().trim();
        _phoneNumber = PhoneNumber.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        final String _completePhoneNumber = "+" + CCP.getFullNumber() + _phoneNumber;

        Query checkPhoneNumber = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("doctorID").equalTo(DoctorUser);
        checkPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DoctorID.setError(null);
                    DoctorID.setErrorEnabled(false);

                    String SystemPhoneNumber = dataSnapshot.child(DoctorUser).child("_phoneNo").getValue(String.class);
                    if (SystemPhoneNumber.equals(_completePhoneNumber)){
                        PhoneNumber.setError(null);
                        PhoneNumber.setErrorEnabled(false);

                        Intent intent = new Intent(getApplicationContext(), VerifyOTP2.class);
                        intent.putExtra("phoneNo", _completePhoneNumber);
                        intent.putExtra("DoctorID", DoctorUser);
                        intent.putExtra("whatToDO", "updateData");
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(DFPassActivity.this, getString(R.string.docter_id_and_phone_number_does_not_match), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    DoctorID.setError(getString(R.string.no_such_user_exist));
                    DoctorID.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DFPassActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validatePhoneNumber() {
        String val = PhoneNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            PhoneNumber.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (val.length() > 10) {
            PhoneNumber.setError(getString(R.string.phone_number_is_too_long));
            return false;
        } else {
            PhoneNumber.setError(null);
            PhoneNumber.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUserID() {
        String val = DoctorID.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            DoctorID.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else {
            DoctorID.setError(null);
            DoctorID.setErrorEnabled(false);
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