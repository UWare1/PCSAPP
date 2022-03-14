package com.example.pcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class DRegisActivity extends AppCompatActivity {

    TextInputLayout DocterID, Pincode, ConPass, Email, NationalIDCard;
    ImageView TitleText, Back;
    Button Next, Patient, Doctor;
    boolean x, y;
    String DoctorString, PinCodeString, EmailString, NationalString, a, b;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dregis);

        DocterID = findViewById(R.id.DocterID);
        Pincode = findViewById(R.id.Pincode);
        ConPass = findViewById(R.id.ConPass);
        Email = findViewById(R.id.Email);
        NationalIDCard = findViewById(R.id.NationalIDCard);
        Next = findViewById(R.id.Next);
        Patient = findViewById(R.id.Patient);
        Doctor = findViewById(R.id.Doctor);
        TitleText = findViewById(R.id.titletext);
        Back = findViewById(R.id.Back);
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

    public void CallNextRegister(View view) {

        DoctorString = DocterID.getEditText().getText().toString().trim();
        PinCodeString = Pincode.getEditText().getText().toString().trim();
        EmailString = Email.getEditText().getText().toString().trim();
        NationalString = NationalIDCard.getEditText().getText().toString().trim();

        if (!CheckDoctorinDB() | !CheckEmailinDB() | !validateDoctorID() | !validatePincode() | !validateConfirmPincode() | !validateEmail() | !validateNationalIDCard()) {
            return;
        }

        Intent call = new Intent(getApplicationContext(), DRegis2Activity.class);

        call.putExtra("DoctorID", DoctorString);
        call.putExtra("Pincode", PinCodeString);
        call.putExtra("Email", EmailString);
        call.putExtra("NationalIDCard", NationalString);

        a = ConPass.getEditText().getText().toString().trim();
        b = Pincode.getEditText().getText().toString().trim();

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Next, "transition_nextbtn");
        pairs[2] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[3] = new Pair<View, String>(Doctor, "transition_doctor_btn");
        pairs[4] = new Pair<View, String>(Back, "transition_back");

        if (a.equals(b)) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegisActivity.this, pairs);
            startActivity(call, options.toBundle());
        } else {
            Toast.makeText(DRegisActivity.this, getString(R.string.pincode_mismatch), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckDoctorinDB() {
        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("doctorID").equalTo(DoctorString);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DocterID.setError(getString(R.string.doctor_has_been_use));
                    DocterID.setErrorEnabled(true);
                    x = false;
                } else {
                    DocterID.setError(null);
                    DocterID.setErrorEnabled(false);
                    x = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DRegisActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
        return x;
    }

    private boolean CheckEmailinDB() {
        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("email").equalTo(EmailString);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Email.setError(getString(R.string.email_has_been_use));
                    Email.setErrorEnabled(true);
                    y = false;
                } else {
                    Email.setError(null);
                    Email.setErrorEnabled(false);
                    y = true;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DRegisActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
        return y;
    }

    public void CallBack(View view) {

        Intent call = new Intent(getApplicationContext(), MainDoctorActivity.class);

        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View, String>(TitleText, "transition_title");
        pairs[1] = new Pair<View, String>(Patient, "transition_patient_btn");
        pairs[2] = new Pair<View, String>(Doctor, "transition_doctor_btn");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(DRegisActivity.this, pairs);
            startActivity(call, options.toBundle());
        } else {
            startActivity(call);
        }
    }

    private boolean validateDoctorID() {
        String val = DocterID.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            DocterID.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (val.length() > 20) {
            DocterID.setError(getString(R.string.docter_id_is_too_long));
            return false;
        } else if (!val.matches(checkspaces)) {
            DocterID.setError(getString(R.string.no_white_spaces_are_allowed));
            return false;
        } else {
            DocterID.setError(null);
            DocterID.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePincode() {
        String val = Pincode.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        Pattern special = Pattern.compile("[!/@#$<>?}{)(%^&+=*+-]");
        if (val.isEmpty()) {
            Pincode.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (!val.matches(checkPassword)) {
            Pincode.setError(getString(R.string.pincode_should_contain_4_character));
            return false;
        } else if (val.contains(" ")) {
            Pincode.setError(getString(R.string.pincode_should_not_space));
            return false;
        } else if (special.matcher(val).find()) {
            Pincode.setError(getString(R.string.pincode_should_have_alphabet_or_number));
            return false;
        } else {
            Pincode.setError(null);
            Pincode.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPincode() {
        String val = ConPass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        Pattern special = Pattern.compile("[!/@#$<>?}{)(%^&+=*+-]");
        if (val.isEmpty()) {
            ConPass.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (!val.matches(checkPassword)) {
            ConPass.setError(getString(R.string.pincode_should_contain_4_character));
            return false;
        } else if (val.contains(" ")) {
            ConPass.setError(getString(R.string.pincode_should_not_space));
            return false;
        } else if (special.matcher(val).find()) {
            ConPass.setError(getString(R.string.pincode_should_have_alphabet_or_number));
            return false;
        } else {
            ConPass.setError(null);
            ConPass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = Email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            Email.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (!val.matches(checkEmail)) {
            Email.setError(getString(R.string.invalid_email));
            return false;
        } else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNationalIDCard() {
        String val = NationalIDCard.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            NationalIDCard.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else if (val.length() > 13) {
            NationalIDCard.setError(getString(R.string.nationalidcard_toolarge));
            return false;
        } else if (val.length() < 13) {
            NationalIDCard.setError(getString(R.string.national_id_card_is_too_short));
            return false;
        } else {
            NationalIDCard.setError(null);
            NationalIDCard.setErrorEnabled(false);
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