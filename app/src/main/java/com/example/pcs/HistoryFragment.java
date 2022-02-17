package com.example.pcs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


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
                final TextInputLayout Pressure = (TextInputLayout) bottomSheetView.findViewById(R.id.Pressure);
                final TextInputLayout Temperature = (TextInputLayout) bottomSheetView.findViewById(R.id.Temperature);
                final TextInputLayout Description = (TextInputLayout) bottomSheetView.findViewById(R.id.Description);
                Date.setText("Date: " + currentDate);
                Time.setText("Time: " + currentTime);

                Dated = Date.getText().toString().trim();
                Timed = Time.getText().toString().trim();
                bottomSheetView.findViewById(R.id.ADD).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Press = Pressure.getEditText().getText().toString().trim();
                        Temp  = Temperature.getEditText().getText().toString().trim();
                        Desc  = Description.getEditText().getText().toString().trim();
                        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference reference = rootNode.getReference("PatientInfo");

                        HistoryHelperClass addNewHistory = new HistoryHelperClass(Dated, Timed, Press, Temp, Desc);
                        reference.child(Dated).child(Timed).setValue(addNewHistory);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        return view;
    }
}