package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class PRegis3Activity extends AppCompatActivity {

    ImageView TitleText;
    Button Next, Patient, Doctor;
    TextInputLayout PhoneNumber;
    EditText Phone;
    CountryCodePicker CCP;
    RelativeLayout S3SSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregis3);

        Next        = findViewById(R.id.Next);
        Patient     = findViewById(R.id.Patient);
        Doctor      = findViewById(R.id.Doctor);
        TitleText   = findViewById(R.id.titletext);
        CCP         = findViewById(R.id.CCP);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        Phone       = findViewById(R.id.Phone);
        S3SSV       = findViewById(R.id.S3SSV);

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

    public void CallVerifyOTPScreen(View view) {

        /*if (!validatePhoneNumber()){
            return;
        }*/

        String _getUserEnteredPhoneNumber = PhoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        String _phoneNo = "+" + CCP.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent call = new Intent(getApplicationContext(), VerifyOTP.class);

        call.putExtra("phoneNo", _phoneNo);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(S3SSV, "transition_OTP_screen");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PRegis3Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }

    }

}