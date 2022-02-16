package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView Pin_View;
    String codeBySystem;
    String UserID, Password, Email, NationalIDCard,
            Fullname, Address, Medical, Allergy, _phoneNo;
    TextView otpDescriptionText;
    ImageView Exit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        Pin_View = findViewById(R.id.Pin_View);
        otpDescriptionText = findViewById(R.id.otpDescriptionText);
        Exit = findViewById(R.id.Exit);

        mAuth = FirebaseAuth.getInstance();

        _phoneNo       = getIntent().getStringExtra("phoneNo");
        UserID         = getIntent().getStringExtra("UserID");
        Password       = getIntent().getStringExtra("Password");
        Email          = getIntent().getStringExtra("Email");
        NationalIDCard = getIntent().getStringExtra("NationalIDCard");
        Fullname       = getIntent().getStringExtra("Fullname");
        Address        = getIntent().getStringExtra("Address");
        Medical        = getIntent().getStringExtra("Medical");
        Allergy        = getIntent().getStringExtra("Allergy");

        otpDescriptionText.setText("Enter One Time Password Sent On " + _phoneNo);

        SendVerificationCodeToUser(_phoneNo);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Ex = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Ex);
                finish();
            }
        });
    }

    private void SendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth) //mAuth is defined on top
                .setPhoneNumber(phoneNo)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code!=null){
                        Pin_View.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyOTP.this, "Verification Cpmpleted", Toast.LENGTH_SHORT).show();
                            storeNewUsersData();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void storeNewUsersData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference reference = rootNode.getReference("Teacher");

        UserHelperClass addNewUser = new UserHelperClass(UserID, Password, Email, NationalIDCard,
                Fullname, Address, Medical, Allergy, _phoneNo);
        reference.child(UserID).setValue(addNewUser);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void callNextScreenFromOTP(View view){

        String code = Pin_View.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
        }
    }
}