package com.example.pcs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    View view;
    LinearLayout Func1, Func2, Func3, //ShowObject1, ShowObject2, ShowObject3,
            Before, After;
    ScrollView ShowObject1, ShowObject2, ShowObject3;
    TextInputLayout CheckDoctorID, CheckUID;
    TextView    Func1Text, Func2Text, Func3Text,
                TypeMents1, TypeMents2, TypeMents3, DateMents1, DateMents2, DateMents3, NameMents1, NameMents2, NameMents3;
    ImageView Func1Image, Func2Image, Func3Image;
    Drawable image, image2, buttonDrawable1, buttonDrawable2, buttonDrawable3;
    AnimatorSet animSet1, animSet2;
    int NumberOfMents;
    String  UserID, MyDoctor, doctored, uid,
            NameDBDoctor, FullnameDBDoctor, EmailDBDoctor, PhoneDBDoctor, UniversityDBDoctor, AddressDBDoctor,
            AboutMents1, AboutMents2, AboutMents3;
    TextView NameDoctor, EmailDoctor, PhoneDoctor, UniversityDoctor, AddressDoctor;
    Button ConnectionRequest;
    CardView AddCommentChat;

    public ChatFragment() {
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
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        Func1 = view.findViewById(R.id.Func1);
        Func2 = view.findViewById(R.id.Func2);
        Func3 = view.findViewById(R.id.Func3);
        Func1Text = view.findViewById(R.id.Func1Text);
        Func2Text = view.findViewById(R.id.Func2Text);
        Func3Text = view.findViewById(R.id.Func3Text);
        Func1Image = view.findViewById(R.id.Func1Image);
        Func2Image = view.findViewById(R.id.Func2Image);
        Func3Image = view.findViewById(R.id.Func3Image);
        TypeMents1 = view.findViewById(R.id.TypeMents1);
        TypeMents2 = view.findViewById(R.id.TypeMents2);
        TypeMents3 = view.findViewById(R.id.TypeMents3);
        DateMents1 = view.findViewById(R.id.DateMents1);
        DateMents2 = view.findViewById(R.id.DateMents2);
        DateMents3 = view.findViewById(R.id.DateMents3);
        NameMents1 = view.findViewById(R.id.NameMents1);
        NameMents2 = view.findViewById(R.id.NameMents2);
        NameMents3 = view.findViewById(R.id.NameMents3);
        AddCommentChat = view.findViewById(R.id.AddCommentChat);
        ShowObject1 = view.findViewById(R.id.ShowObject1);
        ShowObject2 = view.findViewById(R.id.ShowObject2);
        ShowObject3 = view.findViewById(R.id.ShowObject3);
        NameDoctor = view.findViewById(R.id.NameDoctor);
        EmailDoctor = view.findViewById(R.id.EmailDoctor);
        PhoneDoctor = view.findViewById(R.id.PhoneDoctor);
        UniversityDoctor = view.findViewById(R.id.UniversityDoctor);
        AddressDoctor = view.findViewById(R.id.AddressDoctor);
        Before = view.findViewById(R.id.Before);
        After = view.findViewById(R.id.After);
        CheckDoctorID = view.findViewById(R.id.CheckDocterID);
        CheckUID = view.findViewById(R.id.CheckUID);
        ConnectionRequest = view.findViewById(R.id.ConnectionRequest);

        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> UserDetails = sessionManager.getUserDatailFromSession();
        UserID = UserDetails.get(SessionManager.KEY_USERID);

        Query CheckHas = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child(UserID);
        CheckHas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                Map map = (Map) dataSnapshot.getValue();
                NumberOfMents = Integer.parseInt(String.valueOf(map.get("numberOfMents")));
                MyDoctor = String.valueOf(map.get("myDoctor"));
                if (MyDoctor.equals("0")) {
                    Before.setVisibility(View.VISIBLE);
                    After.setVisibility(View.INVISIBLE);
                    animSet1.setTarget(Before);
                    animSet1.start();
                } else {
                    Before.setVisibility(View.INVISIBLE);
                    After.setVisibility(View.VISIBLE);
                    animSet1.setTarget(After);
                    animSet1.start();
                    NameDBDoctor = String.valueOf(map.get("nameDoctor"));
                    Query CheckDoctor = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").child(NameDBDoctor);
                    CheckDoctor.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map mapDoctor = (Map) snapshot.getValue();
                            FullnameDBDoctor = String.valueOf(mapDoctor.get("fullname"));
                            EmailDBDoctor = String.valueOf(mapDoctor.get("email"));
                            PhoneDBDoctor = String.valueOf(mapDoctor.get("_phoneNo"));
                            UniversityDBDoctor = String.valueOf(mapDoctor.get("university"));
                            AddressDBDoctor = String.valueOf(mapDoctor.get("address"));

                            NameDoctor.setText(FullnameDBDoctor);
                            EmailDoctor.setText(EmailDBDoctor);
                            PhoneDoctor.setText(PhoneDBDoctor);
                            UniversityDoctor.setText(UniversityDBDoctor);
                            AddressDoctor.setText(AddressDBDoctor);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        ConnectionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateFields()) {
                    return;
                }
                doctored = CheckDoctorID.getEditText().getText().toString().trim();
                uid = CheckUID.getEditText().getText().toString().trim();
                Query CheckDTHas = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("doctorID").equalTo(doctored);
                CheckDTHas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            CheckDoctorID.setError(null);
                            CheckDoctorID.setErrorEnabled(false);
                            animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                            String uidDB = dataSnapshot.child(doctored).child("uid").getValue(String.class);
                            if (uidDB.equals(uid)) {
                                CheckHas.getRef().child("myDoctor").setValue(uid);
                                CheckHas.getRef().child("nameDoctor").setValue(doctored);
                                CheckHas.getRef().child("color").setValue("None");
                                CheckDTHas.getRef().child(doctored).child("PatientInCare").child(UserID).child("name").setValue(UserID);
                                Before.setVisibility(View.INVISIBLE);
                                After.setVisibility(View.VISIBLE);
                                animSet1.setTarget(After);
                                animSet1.start();
                            } else {
                                CheckUID.setError("UID not match!");
                                CheckUID.setErrorEnabled(true);
                            }
                        } else {
                            CheckDoctorID.setError("DoctorID not has!");
                            CheckDoctorID.setErrorEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        int resID = getResources().getIdentifier("personaldoctorbuttononclick", "drawable", getActivity().getPackageName());
        image = getResources().getDrawable(resID);
        int resID1 = getResources().getIdentifier("personaldoctorbutton", "drawable", getActivity().getPackageName());
        image2 = getResources().getDrawable(resID1);
        Func1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set Your Any Func1
                Func1.setBackground(image);
                Func1Text.setTextColor(Color.WHITE);
                buttonDrawable1 = Func1Image.getBackground();
                buttonDrawable1 = DrawableCompat.wrap(buttonDrawable1);
                DrawableCompat.setTint(buttonDrawable1, Color.WHITE);
                Func1Image.setBackground(buttonDrawable1);
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                animSet1.setTarget(Func1);

                //Set Another Func
                Func2.setBackground(image2);
                Func2Text.setTextColor(Color.GRAY);
                buttonDrawable2 = Func2Image.getBackground();
                buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                DrawableCompat.setTint(buttonDrawable2, Color.GRAY);
                Func2Image.setBackground(buttonDrawable2);

                Func3.setBackground(image2);
                Func3Text.setTextColor(Color.GRAY);
                buttonDrawable3 = Func3Image.getBackground();
                buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                DrawableCompat.setTint(buttonDrawable3, Color.GRAY);
                Func3Image.setBackground(buttonDrawable3);

                //------------------------------------------------------------
                animSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fadeinto);
                animSet2.setTarget(ShowObject1);
                animSet2.start();
                ShowObject1.setVisibility(View.VISIBLE);
                ShowObject2.setVisibility(View.INVISIBLE);
                ShowObject3.setVisibility(View.INVISIBLE);

                //------------------------------------------------------------
                if (AddCommentChat.getVisibility() == View.VISIBLE){
                    AddCommentChat.animate().alpha(0).setDuration(600);
                    //AddCommentChat.setVisibility(View.INVISIBLE);
                    Func3Text.animate().translationY(8).setDuration(600);
                    Func3Image.animate().translationY(8).setDuration(600);
                }
                animSet1.start();
            }
        });
        Func2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set Your Any Func1
                Func2.setBackground(image);
                Func2Text.setTextColor(Color.WHITE);
                buttonDrawable1 = Func2Image.getBackground();
                buttonDrawable1 = DrawableCompat.wrap(buttonDrawable1);
                DrawableCompat.setTint(buttonDrawable1, Color.WHITE);
                Func2Image.setBackground(buttonDrawable1);
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                animSet1.setTarget(Func2);

                //Set Another Func
                Func1.setBackground(image2);
                Func1Text.setTextColor(Color.GRAY);
                buttonDrawable2 = Func1Image.getBackground();
                buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                DrawableCompat.setTint(buttonDrawable2, Color.GRAY);
                Func1Image.setBackground(buttonDrawable2);

                Func3.setBackground(image2);
                Func3Text.setTextColor(Color.GRAY);
                buttonDrawable3 = Func3Image.getBackground();
                buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                DrawableCompat.setTint(buttonDrawable3, Color.GRAY);
                Func3Image.setBackground(buttonDrawable3);

                //------------------------------------------------------------
                animSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fadeinto);
                animSet2.setTarget(ShowObject2);
                animSet2.start();
                ShowObject2.setVisibility(View.VISIBLE);
                ShowObject1.setVisibility(View.INVISIBLE);
                ShowObject3.setVisibility(View.INVISIBLE);

                //------------------------------------------------------------
                if (AddCommentChat.getVisibility() == View.VISIBLE){
                    AddCommentChat.animate().alpha(0).setDuration(600);
                    //AddCommentChat.setVisibility(View.INVISIBLE);
                    Func3Text.animate().translationY(8).setDuration(600);
                    Func3Image.animate().translationY(8).setDuration(600);
                }
                animSet1.start();
            }
        });
        Func3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set Your Any Func1
                Func3.setBackground(image);
                Func3Text.setTextColor(Color.WHITE);
                buttonDrawable1 = Func3Image.getBackground();
                buttonDrawable1 = DrawableCompat.wrap(buttonDrawable1);
                DrawableCompat.setTint(buttonDrawable1, Color.WHITE);
                Func3Image.setBackground(buttonDrawable1);
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                animSet1.setTarget(Func3);

                //Set Another Func
                Func1.setBackground(image2);
                Func1Text.setTextColor(Color.GRAY);
                buttonDrawable2 = Func1Image.getBackground();
                buttonDrawable2 = DrawableCompat.wrap(buttonDrawable2);
                DrawableCompat.setTint(buttonDrawable2, Color.GRAY);
                Func1Image.setBackground(buttonDrawable2);

                Func2.setBackground(image2);
                Func2Text.setTextColor(Color.GRAY);
                buttonDrawable3 = Func2Image.getBackground();
                buttonDrawable3 = DrawableCompat.wrap(buttonDrawable3);
                DrawableCompat.setTint(buttonDrawable3, Color.GRAY);
                Func2Image.setBackground(buttonDrawable3);

                //------------------------------------------------------------
                animSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fadeinto);
                animSet2.setTarget(ShowObject3);
                animSet2.start();
                ShowObject3.setVisibility(View.VISIBLE);
                ShowObject1.setVisibility(View.INVISIBLE);
                ShowObject2.setVisibility(View.INVISIBLE);

                //------------------------------------------------------------
                AddCommentChat.setVisibility(View.VISIBLE);
                animSet1.setTarget(AddCommentChat);
                animSet1.start();
                Func3Text.animate().translationY(-20).setDuration(600);
                Func3Image.animate().translationY(-20).setDuration(600);

                //------------------------------------------------------------ Error not have AboutMents in Database (New Register)
                AboutMents1 = "AboutMents " + NumberOfMents;
                AboutMents2 = "AboutMents " + (NumberOfMents - 1);
                AboutMents3 = "AboutMents " + (NumberOfMents - 2);

                Query CheckPatient1 = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").child(NameDBDoctor).child("PatientInCare").child(UserID).child(AboutMents1).getRef();
                CheckPatient1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map mapMents = (Map) dataSnapshot.getValue();
                        TypeMents1.setText(String.valueOf(mapMents.get("typeComment")));
                        DateMents1.setText(String.valueOf(mapMents.get("currentDate")));
                        NameMents1.setText(String.valueOf(mapMents.get("nameComment")));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                Query CheckPatient2 = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").child(NameDBDoctor).child("PatientInCare").child(UserID).child(AboutMents2).getRef();
                CheckPatient2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map mapMents = (Map) dataSnapshot.getValue();
                        TypeMents2.setText(String.valueOf(mapMents.get("typeComment")));
                        DateMents2.setText(String.valueOf(mapMents.get("currentDate")));
                        NameMents2.setText(String.valueOf(mapMents.get("nameComment")));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                Query CheckPatient3 = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").child(NameDBDoctor).child("PatientInCare").child(UserID).child(AboutMents3).getRef();
                CheckPatient3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map mapMents = (Map) dataSnapshot.getValue();
                        TypeMents3.setText(String.valueOf(mapMents.get("typeComment")));
                        DateMents3.setText(String.valueOf(mapMents.get("currentDate")));
                        NameMents3.setText(String.valueOf(mapMents.get("nameComment")));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AddCommentChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet3,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer)
                        );
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
        return view;
    }

    private boolean validateFields() {

        String user = CheckDoctorID.getEditText().getText().toString().trim();
        String pass = CheckUID.getEditText().getText().toString().trim();

        if (user.isEmpty()) {
            CheckDoctorID.setError(getString(R.string.user_empty));
            CheckDoctorID.requestFocus();
            return false;
        } else if (pass.isEmpty()) {
            CheckUID.setError(getString(R.string.password_empty));
            CheckUID.requestFocus();
            return false;
        } else {
            CheckDoctorID.setError(null);
            CheckDoctorID.setErrorEnabled(false);
            CheckUID.setError(null);
            CheckUID.setErrorEnabled(false);
            return true;
        }
    }
}