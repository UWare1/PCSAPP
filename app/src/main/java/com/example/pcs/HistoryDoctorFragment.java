package com.example.pcs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    LinearLayout LayH01, HistoryDoctorLay;
    TextInputLayout Comments;
    View view;
    TextView SelectPatient, NamePatient, EmailPatient, MoreDetails,
            NameSelect, EmailSelect, PhoneSelect, BirthSelect, NationalIDCardSelect, AddressSelect, AllergySelect, MedicalSelect;
    ImageView ImagePatient, GenderImage;
    Button CommentsPatient, ColorPatient;

    int NumberOfMents, resID;
    Drawable image;

    String currentDate, currentTime,
            DoctorID, PatientID,
            FullnameDBPatient, EmailDBPatient, ProfileIDDBPatient,
            PhoneDBPatient, DateDBPatient, NationalIDCardDBPatient,
            AddressDBPatient, AllergyDBPatient, MedicalDBPatient, GenderDBPatient,
            TypeComment, NameComments, NameColor;
    AnimatorSet animSet1;

    RecyclerView recyclerView;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
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
        MoreDetails = view.findViewById(R.id.MoreDetails);
        CommentsPatient = view.findViewById(R.id.CommentsPatient);
        ColorPatient = view.findViewById(R.id.ColorPatient);

        HistoryDoctorLay = view.findViewById(R.id.HistoryDoctorLay);
        LayH01 = view.findViewById(R.id.LayH01);
        recyclerView = view.findViewById(R.id.recyclerviewdoctor);
        //-----------------------------------------------------
        animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
        animSet1.setTarget(HistoryDoctorLay);
        animSet1.start();

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

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
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String parent = childSnapshot.getKey();
                    arrayList.add(parent);
                    //Log.i("TAG", parent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
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

                        databaseReference = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child(PatientID).child("PatientInfo");
                        list = new ArrayList<>();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setReverseLayout(true);
                        layoutManager.setStackFromEnd(true);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new MyAdapter(getContext(), list);
                        recyclerView.setAdapter(adapter);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                list.clear();
                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    User user = dataSnapshot.getValue(User.class);
                                    list.add(user);
                                }
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        Query CheckInsidePatient = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child(PatientID);
                        CheckInsidePatient.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map map = (Map) snapshot.getValue();
                                FullnameDBPatient = String.valueOf(map.get("fullname"));
                                EmailDBPatient = String.valueOf(map.get("email"));
                                ProfileIDDBPatient = String.valueOf(map.get("profileID"));
                                PhoneDBPatient = String.valueOf(map.get("_phoneNo"));
                                DateDBPatient = String.valueOf(map.get("date"));
                                NationalIDCardDBPatient = String.valueOf(map.get("nationalIDCard"));
                                AddressDBPatient = String.valueOf(map.get("address"));
                                AllergyDBPatient = String.valueOf(map.get("allergy"));
                                MedicalDBPatient = String.valueOf(map.get("medical"));
                                GenderDBPatient = String.valueOf(map.get("gender"));

                                NameColor = String.valueOf(map.get("color"));
                                NumberOfMents = Integer.parseInt(String.valueOf(map.get("numberOfMents")));

                                NamePatient.setText(FullnameDBPatient);
                                EmailPatient.setText(EmailDBPatient);
                                int resID = getResources().getIdentifier(ProfileIDDBPatient, "drawable", getActivity().getPackageName());
                                Drawable image = getResources().getDrawable(resID);
                                ImagePatient.setImageDrawable(image);

                                CommentsPatient.setVisibility(View.VISIBLE);
                                CommentsPatient.animate().alpha(1).setDuration(300);

                                MoreDetails.setVisibility(View.VISIBLE);
                                MoreDetails.animate().alpha(1).setDuration(300);

                                ColorPatient.setText(getString(R.string.color) + " : " + NameColor);
                                ColorPatient.setVisibility(View.VISIBLE);
                                ColorPatient.animate().alpha(1).setDuration(300);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
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
                Comments = (TextInputLayout) bottomSheetView.findViewById(R.id.AllComments);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = (RadioButton) bottomSheetView.findViewById(checkedId);
                        TypeComment = rb.getText().toString();
                    }
                });
                bottomSheetView.findViewById(R.id.SendComments).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validateComments()) {
                            return;
                        }
                        NameComments = Comments.getEditText().getText().toString().trim();
                        NumberOfMents++;
                        String AboutMent = "AboutMents " + NumberOfMents;
                        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference reference = rootNode.getReference("Doctor");
                        DatabaseReference referencePatient = rootNode.getReference("Teacher");
                        
                        referencePatient.child(PatientID).child("numberOfMents").setValue(NumberOfMents);
                        reference.child(DoctorID).child("PatientInCare").child(PatientID).child(AboutMent).child("typeComment").setValue(TypeComment);
                        reference.child(DoctorID).child("PatientInCare").child(PatientID).child(AboutMent).child("nameComment").setValue(NameComments);
                        reference.child(DoctorID).child("PatientInCare").child(PatientID).child(AboutMent).child("currentDate").setValue(currentDate);
                        reference.child(DoctorID).child("PatientInCare").child(PatientID).child(AboutMent).child("currentTime").setValue(currentTime);

                        bottomSheetDialog.dismiss();
                        Toast.makeText(mContext, getString(R.string.add2) + TypeComment + getString(R.string.success), Toast.LENGTH_SHORT).show();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet7,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer)
                        );
                NameSelect = bottomSheetView.findViewById(R.id.NameSelect);
                EmailSelect = bottomSheetView.findViewById(R.id.EmailSelect);
                PhoneSelect = bottomSheetView.findViewById(R.id.PhoneSelect);
                BirthSelect = bottomSheetView.findViewById(R.id.BirthSelect);
                NationalIDCardSelect = bottomSheetView.findViewById(R.id.NationalIDCardSelect);
                AddressSelect = bottomSheetView.findViewById(R.id.AddressSelect);
                AllergySelect = bottomSheetView.findViewById(R.id.AllergySelect);
                MedicalSelect = bottomSheetView.findViewById(R.id.MedicalSelect);
                GenderImage = bottomSheetView.findViewById(R.id.GenderImage);

                String LastName = "";
                String FirstName = "";
                if (FullnameDBPatient.split("\\w+").length > 1) {

                    LastName = FullnameDBPatient.substring(FullnameDBPatient.lastIndexOf(" ") + 1);
                    FirstName = FullnameDBPatient.substring(0, FullnameDBPatient.lastIndexOf(' '));
                } else {
                    FirstName = FullnameDBPatient;
                }
                NameSelect.setText(FirstName + "\n" + LastName);
                EmailSelect.setText(EmailDBPatient);
                PhoneSelect.setText(PhoneDBPatient);
                BirthSelect.setText(DateDBPatient);
                NationalIDCardSelect.setText(NationalIDCardDBPatient);
                AddressSelect.setText(AddressDBPatient);
                AllergySelect.setText(AllergyDBPatient);
                MedicalSelect.setText(MedicalDBPatient);

                if (GenderDBPatient.equals("ผู้ชาย") | GenderDBPatient.equals("Male")) {
                    resID = getResources().getIdentifier("man", "drawable", getActivity().getPackageName());
                    image = getResources().getDrawable(resID);
                    GenderImage.setImageDrawable(image);
                } else if (GenderDBPatient.equals("ผู้หญิง") | GenderDBPatient.equals("Female")) {
                    resID = getResources().getIdentifier("woman", "drawable", getActivity().getPackageName());
                    image = getResources().getDrawable(resID);
                    GenderImage.setImageDrawable(image);
                } else {
                    resID = getResources().getIdentifier("bigender", "drawable", getActivity().getPackageName());
                    image = getResources().getDrawable(resID);
                    GenderImage.setImageDrawable(image);
                }

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
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = (RadioButton) bottomSheetView.findViewById(checkedId);
                        NameColor = rb.getText().toString();
                    }
                });
                bottomSheetView.findViewById(R.id.Understand).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorPatient.setText(getString(R.string.color) + " : " + NameColor);

                        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference referencePatient = rootNode.getReference("Teacher");

                        referencePatient.child(PatientID).child("color").setValue(NameColor);
                        bottomSheetDialog.dismiss();
                        Toast.makeText(mContext, getString(R.string.add_color_success), Toast.LENGTH_SHORT).show();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        //---------------------------------------------------------
        return view;
    }

    private boolean validateComments() {
        String val = Comments.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            Comments.setError(getString(R.string.field_can_not_be_empty));
            return false;
        } else {
            Comments.setError(null);
            Comments.setErrorEnabled(false);
            return true;
        }
    }
}