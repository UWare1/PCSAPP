package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainDoctorActivity extends AppCompatActivity {

    LinearLayout Lay, Lay2, Lay3, Lay4;
    String DoctorUser, Pin;
    TextInputLayout DoctorID, Pincode;
    TextView FPassword, Register;
    View Facebook, Google, Twitter, Line;
    ImageView TitleText;
    Button Login, Patient, Doctor;
    Animation FadeInUp;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

        DoctorID  = findViewById(R.id.DoctorID);
        Pincode   = findViewById(R.id.Pincode);
        TitleText = findViewById(R.id.titletext);
        FPassword = findViewById(R.id.FPassword);
        Register = findViewById(R.id.Register);
        Login = findViewById(R.id.Login);
        Patient = findViewById(R.id.Patient);
        Doctor = findViewById(R.id.Doctor);
        Facebook = findViewById(R.id.Facebook);
        Google = findViewById(R.id.Google);
        Twitter = findViewById(R.id.Twitter);
        Line = findViewById(R.id.Line);
        Lay = findViewById(R.id.lay1);
        Lay2 = findViewById(R.id.lay2);
        Lay3 = findViewById(R.id.lay3);
        Lay4 = findViewById(R.id.lay4);
        decorView = getWindow().getDecorView();

        FadeInUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeinup);
        Lay.startAnimation(FadeInUp);
        Lay2.startAnimation(FadeInUp);
        Lay3.startAnimation(FadeInUp);
        Lay4.startAnimation(FadeInUp);


        FPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Forgot = new Intent(getApplicationContext(), DFPassActivity.class);
                startActivity(Forgot);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regis = new Intent(getApplicationContext(), DRegisActivity.class);
                startActivity(Regis);
                finish();
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
                RestartActivity();
            }
        });
    }

    public void letTheUserLoggedIn(View view) {

        /*if (!isConnected(this)) {
            showCustomDialog();
        }*/

        if (!validateFields()) {
            return;
        }

        DoctorUser = DoctorID.getEditText().getText().toString().trim();
        Pin = Pincode.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("doctorID").equalTo(DoctorUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DoctorID.setError(null);
                    DoctorID.setErrorEnabled(false);
                    String SystemPassword = dataSnapshot.child(DoctorUser).child("pincode").getValue(String.class);
                    if (SystemPassword.equals(Pin)) {
                        Pincode.setError(null);
                        Pincode.setErrorEnabled(false);

                        String Fullname1 = dataSnapshot.child(DoctorUser).child("fullname").getValue(String.class);
                        String Email1 = dataSnapshot.child(DoctorUser).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(DoctorUser).child("_phoneNo").getValue(String.class);
                        String _address = dataSnapshot.child(DoctorUser).child("address").getValue(String.class);
                        String _regularhospital = dataSnapshot.child(DoctorUser).child("regularHospital").getValue(String.class);
                        String _university = dataSnapshot.child(DoctorUser).child("university").getValue(String.class);
                        String _nationalIDCard = dataSnapshot.child(DoctorUser).child("nationalIDCard").getValue(String.class);
                        String _pincode = dataSnapshot.child(DoctorUser).child("pincode").getValue(String.class);
                        String _doctorID = dataSnapshot.child(DoctorUser).child("doctorID").getValue(String.class);
                        String _dateOfBirth = dataSnapshot.child(DoctorUser).child("date").getValue(String.class);
                        String _gender = dataSnapshot.child(DoctorUser).child("gender").getValue(String.class);
                        String ProfileID = dataSnapshot.child(DoctorUser).child("profileID").getValue(String.class);
                        String uid = dataSnapshot.child(DoctorUser).child("uid").getValue(String.class);

                        SessionManagerDoctor sessionManager = new SessionManagerDoctor(MainDoctorActivity.this);
                        sessionManager.createLoginSession(Fullname1, _doctorID, Email1, _phoneNo, _pincode, _dateOfBirth, _gender, ProfileID, _nationalIDCard, _address, _regularhospital, _university, uid);

                        Intent intent = new Intent(getApplicationContext(), Main2DoctorActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainDoctorActivity.this, "Pincode does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainDoctorActivity.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainDoctorActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), LanguagePage.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainDoctorActivity.this, pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }
    private void RestartActivity(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    private boolean validateFields() {

        String user = DoctorID.getEditText().getText().toString().trim();
        String pass = Pincode.getEditText().getText().toString().trim();

        if (user.isEmpty()) {
            DoctorID.setError(getString(R.string.user_empty));
            DoctorID.requestFocus();
            return false;
        } else if (pass.isEmpty()) {
            Pincode.setError(getString(R.string.password_empty));
            Pincode.requestFocus();
            return false;
        } else {
            DoctorID.setError(null);
            DoctorID.setErrorEnabled(false);
            Pincode.setError(null);
            Pincode.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
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
