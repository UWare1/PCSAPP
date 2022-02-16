package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String User, Pass;
    TextInputLayout UserID, Password;
    TextView FPassword, Register;
    View Facebook, Google, Twitter, Line;
    Button Login, Patient, Doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserID    = findViewById(R.id.UserID);
        Password  = findViewById(R.id.Password);
        FPassword = findViewById(R.id.FPassword);
        Register  = findViewById(R.id.Register);
        Login     = findViewById(R.id.Login);
        Patient   = findViewById(R.id.Patient);
        Doctor    = findViewById(R.id.Doctor);
        Facebook  = findViewById(R.id.Facebook);
        Google    = findViewById(R.id.Google);
        Twitter   = findViewById(R.id.Twitter);
        Line      = findViewById(R.id.Line);



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
        });Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Doct = new Intent(getApplicationContext(), MainDoctorActivity.class);
                startActivity(Doct);
                finish();
            }
        });

    }

    private void RestartActivity(){
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void letTheUserLoggedIn(View view) {

        if (!isConnected(this)){
            showCustomDialog();
        }

        if (!validateFields()){
            return;
        }

        User = UserID.getEditText().getText().toString().trim();
        Pass = Password.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").orderByChild("userID").equalTo(User);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserID.setError(null);
                    UserID.setErrorEnabled(false);
                    String SystemPassword = dataSnapshot.child(User).child("password").getValue(String.class);
                    if (SystemPassword.equals(Pass)){
                        Password.setError(null);
                        Password.setErrorEnabled(false);

                        String Fullname1 = dataSnapshot.child(User).child("fullname").getValue(String.class);
                        String Email1 = dataSnapshot.child(User).child("email").getValue(String.class);

                        Toast.makeText(MainActivity.this, Fullname1 + "\n" + Email1 + " ", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
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

        if ((wifiConn != null && wifiConn.isConnected() || (mobileConn != null && mobileConn.isConnected()))){
            return true;
        } else {
            return false;
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

    private boolean validateFields(){

        String user = UserID.getEditText().getText().toString().trim();
        String pass = Password.getEditText().getText().toString().trim();

        if (user.isEmpty()){
            UserID.setError("User ID can not be Empty");
            UserID.requestFocus();
            return false;
        }  else if (pass.isEmpty()){
            Password.setError("Password can not be Empty");
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

}
