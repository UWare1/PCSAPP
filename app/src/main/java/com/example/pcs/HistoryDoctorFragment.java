package com.example.pcs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryDoctorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    LinearLayout LayH01;
    View view;
    TextView SelectPatient, NamePatient, EmailPatient;
    ImageView ImagePatient;
    Button CommentsPatient, ColorPatient;

    String DoctorID, PatientID,
           FullnameDBPatient, EmailDBPatient, ProfileIDDBPatient;

    public HistoryDoctorFragment() {
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
     * @return A new instance of fragment HistoryDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryDoctorFragment newInstance(String param1, String param2) {
        HistoryDoctorFragment fragment = new HistoryDoctorFragment();
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
        view = inflater.inflate(R.layout.fragment_history_doctor, container, false);

        //---------------------------------------------------------
        SelectPatient = view.findViewById(R.id.SelectPatient);
        NamePatient = view.findViewById(R.id.NamePatient);
        EmailPatient = view.findViewById(R.id.EmailPatient);
        ImagePatient = view.findViewById(R.id.ImagePatient);
        CommentsPatient = view.findViewById(R.id.CommentsPatient);
        ColorPatient = view.findViewById(R.id.ColorPatient);

        LayH01 = view.findViewById(R.id.LayH01);

        //---------------------------------------------------------
        ArrayList<String> arrayList = new ArrayList<>();

        //---------------------------------------------------------
        SessionManagerDoctor sessionManager = new SessionManagerDoctor(getActivity());
        HashMap<String, String> UserDetails = sessionManager.getUserDatailFromSession();
        DoctorID = UserDetails.get(SessionManagerDoctor.KEY_DOCTORID);

        Query CheckPatient = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").child(DoctorID).child("PatientInCare").getRef();
        CheckPatient.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String parent = childSnapshot.getKey();
                    arrayList.add(parent);
                    //Log.i("TAG", parent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        //---------------------------------------------------------
        SelectPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet4,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer2)
                        );

                final ListView ListShowPatient = bottomSheetView.findViewById(R.id.ListShowPatient);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                ListShowPatient.setAdapter(arrayAdapter);
                ListShowPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        PatientID = arrayList.get(i);
                        Query CheckInsidePatient = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child(PatientID);
                        CheckInsidePatient.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map map = (Map) snapshot.getValue();
                                FullnameDBPatient = String.valueOf(map.get("fullname"));
                                EmailDBPatient = String.valueOf(map.get("email"));
                                ProfileIDDBPatient = String.valueOf(map.get("profileID"));

                                NamePatient.setText(FullnameDBPatient);
                                EmailPatient.setText(EmailDBPatient);
                                int resID = getResources().getIdentifier(ProfileIDDBPatient, "drawable", getActivity().getPackageName());
                                Drawable image = getResources().getDrawable(resID);
                                ImagePatient.setImageDrawable(image);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.Understand).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        CommentsPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet5,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer2)
                        );
                final RadioGroup radioGroup = bottomSheetView.findViewById(R.id.Radio_Comment);
                final RadioButton selectedComment = bottomSheetView.findViewById(radioGroup.getCheckedRadioButtonId());
                //final String TypeComment = selectedComment.getText().toString().trim();
                final TextInputLayout Comments = bottomSheetView.findViewById(R.id.AllComments);
                final String NameComment = Comments.getEditText().getText().toString().trim();

                //------------------------------ Save data to Database (TypeComment and NameComment)








                //----------------------------------------------------------------------------------
                bottomSheetView.findViewById(R.id.Understand).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        ColorPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet6,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer2)
                        );
                final RadioGroup radioGroup = bottomSheetView.findViewById(R.id.Radio_Color);
                final RadioButton selectedColor = bottomSheetView.findViewById(radioGroup.getCheckedRadioButtonId());
                //final String NameColor = selectedColor.getText().toString().trim();
                bottomSheetView.findViewById(R.id.Understand).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ColorPatient.setText("Color : " + NameColor);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        //---------------------------------------------------------
        return view;
    }

}