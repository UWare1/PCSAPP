package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class PFPassActivity extends AppCompatActivity {

    ImageView TitleText;
    TextInputLayout PhoneNumber;
    Button Recover, Patient, Doctor;
    CountryCodePicker CCP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfpass);

        PhoneNumber = findViewById(R.id.PhoneNumber);
        Recover   = findViewById(R.id.Recover);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);
        TitleText = findViewById(R.id.titletext);
        CCP         = findViewById(R.id.CCP);

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

        Intent call = new Intent(getApplicationContext(), MainActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[2] = new Pair<View, String>(Doctor, "transition_doctor_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PFPassActivity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    private boolean validatePhoneNumber() {
        String val = PhoneNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            PhoneNumber.setError("Field can not be empty");
            return false;
        } else if (val.length() > 10) {
            PhoneNumber.setError("Phone Number is too large!");
            return false;
        } else {
            PhoneNumber.setError(null);
            PhoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    public void VerifyPhoneNumber(View view) {
        //check net
        //validate phone number
        if(!validatePhoneNumber()){
            return ;
        }



        String _phoneNumber = PhoneNumber.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }
        final String _completePhoneNumber = "+" + CCP.getFullNumber()+_phoneNumber;

        Query checkPhoneNumber = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").orderByChild("_phoneNo").equalTo(_completePhoneNumber);
        checkPhoneNumber.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    PhoneNumber.setError(null);
                    PhoneNumber.setErrorEnabled(false);

                        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                        intent.putExtra("phoneNo",_completePhoneNumber);
                        intent.putExtra("whaToDO","updateData");
                        startActivity(intent);
                        finish();


                }
                else {
                    //progressBar
                    PhoneNumber.setError("No such user exist!");
                    PhoneNumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PFPassActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    }


