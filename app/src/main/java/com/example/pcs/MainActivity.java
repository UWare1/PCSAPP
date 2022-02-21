package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    LinearLayout Lay, Lay2, Lay3, Lay4;
    String User, Pass;
    TextInputLayout UserID, Password;
    TextView FPassword, Register;
    View Facebook, Google, Twitter, Line;
    ImageView TitleText;
    Button Login, Patient, Doctor;
    Animation FadeInUp;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitleText = findViewById(R.id.titletext);
        UserID = findViewById(R.id.UserID);
        Password = findViewById(R.id.Password);
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
                Intent Forgot = new Intent(getApplicationContext(), PFPassActivity.class);
                startActivity(Forgot);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Regis = new Intent(getApplicationContext(), PRegisActivity.class);
                startActivity(Regis);
                finish();
            }
        });
        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartActivity();
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
        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Doct = new Intent(getApplicationContext(), MainDoctorActivity.class);
                startActivity(Doct);
                finish();
            }
        });

    }

    private void RestartActivity() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void letTheUserLoggedIn(View view) {

        if (!isConnected(this)) {
            showCustomDialog();
        }

        if (!validateFields()) {
            return;
        }

        User = UserID.getEditText().getText().toString().trim();
        Pass = Password.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").orderByChild("userID").equalTo(User);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserID.setError(null);
                    UserID.setErrorEnabled(false);
                    String SystemPassword = dataSnapshot.child(User).child("password").getValue(String.class);
                    if (SystemPassword.equals(Pass)) {
                        Password.setError(null);
                        Password.setErrorEnabled(false);

                        String Fullname1 = dataSnapshot.child(User).child("fullname").getValue(String.class);
                        String Email1 = dataSnapshot.child(User).child("email").getValue(String.class);
                        String _phoneNo = dataSnapshot.child(User).child("_phoneNo").getValue(String.class);
                        String _address = dataSnapshot.child(User).child("address").getValue(String.class);
                        String _alleryg = dataSnapshot.child(User).child("allergy").getValue(String.class);
                        String _medical = dataSnapshot.child(User).child("medical").getValue(String.class);
                        String _nationallCard = dataSnapshot.child(User).child("nationallCard").getValue(String.class);
                        String _password = dataSnapshot.child(User).child("password").getValue(String.class);
                        String _userID = dataSnapshot.child(User).child("userID").getValue(String.class);
                        String _dateOfBirth = dataSnapshot.child(User).child("date").getValue(String.class);
                        String _gender = dataSnapshot.child(User).child("gender").getValue(String.class);
                        String ProfileID = dataSnapshot.child(User).child("profileID").getValue(String.class);

                        SessionManager sessionManager = new SessionManager(MainActivity.this);
                        sessionManager.createLoginSession(Fullname1, _userID, Email1, _phoneNo, _password, _dateOfBirth, _gender, ProfileID);

                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No such user exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected(MainActivity mainActivity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected()))) {
            return true;
        } else {
            return false;
        }
    }

    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), LanguagePage.class);

        Pair[] pairs = new Pair[1];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean validateFields() {

        String user = UserID.getEditText().getText().toString().trim();
        String pass = Password.getEditText().getText().toString().trim();

        if (user.isEmpty()) {
            UserID.setError(getString(R.string.user_empty));
            UserID.requestFocus();
            return false;
        } else if (pass.isEmpty()) {
            Password.setError(getString(R.string.password_empty));
            Password.requestFocus();
            return false;
        } else {
            UserID.setError(null);
            UserID.setErrorEnabled(false);
            Password.setError(null);
            Password.setErrorEnabled(false);
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
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}
