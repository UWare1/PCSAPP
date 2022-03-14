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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class DRegis3Activity extends AppCompatActivity {

    ImageView TitleText, Back;
    Button Next, Patient, Doctor;
    TextInputLayout PhoneNumber;
    CountryCodePicker CCP;
    RelativeLayout S3SSV;
    View decorView;
    boolean x;
    String DoctorID, Pincode, Email, NationalIDCard,
            Fullname, Address, RegularHospital, University, Gender, Date, _phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dregis3);

        Next        = findViewById(R.id.Next);
        Patient     = findViewById(R.id.Patient);
        Doctor      = findViewById(R.id.Doctor);
        TitleText   = findViewById(R.id.titletext);
        Back      = findViewById(R.id.Back);
        CCP         = findViewById(R.id.CCP);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        S3SSV       = findViewById(R.id.S3SSV);
        decorView = getWindow().getDecorView();

        DoctorID         = getIntent().getStringExtra("DoctorID");
        Pincode       = getIntent().getStringExtra("Pincode");
        Email          = getIntent().getStringExtra("Email");
        NationalIDCard = getIntent().getStringExtra("NationalIDCard");
        Fullname       = getIntent().getStringExtra("Fullname");
        Address        = getIntent().getStringExtra("Address");
        RegularHospital        = getIntent().getStringExtra("RegularHospital");
        University        = getIntent().getStringExtra("University");
        Gender         = getIntent().getStringExtra("Gender");
        Date           = getIntent().getStringExtra("Date");

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
        String _getUserEnteredPhoneNumber = PhoneNumber.getEditText().getText().toString().trim();
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
        _phoneNo = "+" + CCP.getFullNumber() + _getUserEnteredPhoneNumber;

        if (!CheckPhoneNumberinDB() | !validatePhoneNumber()){
            return;
        }

        Intent call = new Intent(getApplicationContext(), VerifyOTP2.class);

        call.putExtra("phoneNo", _phoneNo);
        call.putExtra("DoctorID", DoctorID);
        call.putExtra("Pincode", Pincode);
        call.putExtra("Email", Email);
        call.putExtra("NationalIDCard", NationalIDCard);
        call.putExtra("Fullname", Fullname);
        call.putExtra("Address", Address);
        call.putExtra("RegularHospital", RegularHospital);
        call.putExtra("University", University);
        call.putExtra("Gender", Gender);
        call.putExtra("Date", Date);
        call.putExtra("whatToDO", "Regis");

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View, String>(S3SSV, "transition_OTP_screen");
        pairs[1] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegis3Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }

    }
    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), DRegis2Activity.class);

        Pair[] pairs = new Pair[5];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegis3Activity.this,pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }

    private boolean CheckPhoneNumberinDB() {
        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("_phoneNo").equalTo(_phoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    PhoneNumber.setError(getString(R.string.phone_number_has_been_use));
                    PhoneNumber.setErrorEnabled(true);
                    x = false;
                } else {
                    PhoneNumber.setError(null);
                    PhoneNumber.setErrorEnabled(false);
                    x = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DRegis3Activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
        return x;
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