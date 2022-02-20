package com.example.pcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    View view;
    Button HistoryRefresh, ADDHistory;
    String currentDate, currentTime,
            Dated, Timed, Press, Temp, Desc;
    TextInputLayout Pressure, Temperature, Description;
    TextView DaTe1;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        HistoryRefresh = view.findViewById(R.id.HistoryRefresh);
        ADDHistory = view.findViewById(R.id.ADDHistory);
        DaTe1 = view.findViewById(R.id.DaTe1);
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        /*SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> UserDetails = sessionManager.getUserDatailFromSession();
        String UserID = UserDetails.get(SessionManager.KEY_USERID);*/


        ADDHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer)
                        );
                final TextView Date = bottomSheetView.findViewById(R.id.Date);
                final TextView Time = bottomSheetView.findViewById(R.id.Time);
                Pressure = (TextInputLayout) bottomSheetView.findViewById(R.id.Pressure);
                Temperature = (TextInputLayout) bottomSheetView.findViewById(R.id.Temperature);
                Description = (TextInputLayout) bottomSheetView.findViewById(R.id.Description);
                Date.setText("Date: " + currentDate);
                Time.setText("Time: " + currentTime);

                Dated = Date.getText().toString().trim();
                Timed = Time.getText().toString().trim();

                bottomSheetView.findViewById(R.id.ADD).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validatePressure() | !validateTemperature() | !validateDescription()) {
                            return;
                        }
                        Press = Pressure.getEditText().getText().toString().trim();
                        Temp = Temperature.getEditText().getText().toString().trim();
                        Desc = Description.getEditText().getText().toString().trim();

                        SessionManager sessionManager = new SessionManager(getActivity());
                        HashMap<String, String> UserDetails = sessionManager.getUserDatailFromSession();
                        String UserID = UserDetails.get(SessionManager.KEY_USERID);

                        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference reference = rootNode.getReference("Teacher");

                        HistoryHelperClass addNewHistory = new HistoryHelperClass(Dated, Timed, Press, Temp, Desc);
                        reference.child(UserID).child("PatientInfo").child(Dated).child(Timed).setValue(addNewHistory);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        return view;
    }

    private boolean validatePressure() {
        String val = Pressure.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Pressure.setError("Field can not be empty");
            return false;
        } else {
            Pressure.setError(null);
            Pressure.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateTemperature() {
        String val = Temperature.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Temperature.setError("Field can not be empty");
            return false;
        } else {
            Temperature.setError(null);
            Temperature.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDescription() {
        String val = Description.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Description.setError("Field can not be empty");
            return false;
        } else {
            Description.setError(null);
            Description.setErrorEnabled(false);
            return true;
        }
    }
}