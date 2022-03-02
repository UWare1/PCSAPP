package com.example.pcs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    boolean x, y;
    View view;
    LinearLayout Func1, Func2, Func3, ShowObject1, ShowObject2, ShowObject3,
                 Before, After;
    TextInputLayout CheckDoctorID, CheckUID;
    TextView Func1Text, Func2Text, Func3Text;
    ImageView Func1Image, Func2Image, Func3Image;
    Drawable image, image2, buttonDrawable1, buttonDrawable2, buttonDrawable3;
    AnimatorSet animSet1, animSet2;
    String DoctorIDString, username, username1;
    Button ConnectionRequest;
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
        ShowObject1 = view.findViewById(R.id.ShowObject1);
        ShowObject2 = view.findViewById(R.id.ShowObject2);
        ShowObject3 = view.findViewById(R.id.ShowObject3);
        Before = view.findViewById(R.id.Before);
        After = view.findViewById(R.id.After);
        CheckDoctorID = view.findViewById(R.id.CheckDocterID);
        CheckUID = view.findViewById(R.id.CheckUID);
        ConnectionRequest = view.findViewById(R.id.ConnectionRequest);
        Query CheckHas = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child("Yutthana");
        CheckHas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if (dataSnapshot.exists()) {
                    Map map = (Map)dataSnapshot.getValue();
                    username = String.valueOf(map.get("address"));
                    username1 = String.valueOf(map.get("uid"));
                    //Toast.makeText(mContext, "Has" + username + " | " + username1, Toast.LENGTH_SHORT).show();
                    /*} else {
                    Toast.makeText(mContext, "Not Has", Toast.LENGTH_SHORT).show();
                }*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        ConnectionRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CheckDoctorinDB()) {
                    return;
                }
                if (!validateFields()){
                    return;
                }
                Before.setVisibility(View.INVISIBLE);
                After.setVisibility(View.VISIBLE);
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                animSet1.setTarget(After);
                animSet1.start();
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
                animSet1.start();

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
                animSet1.start();

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
                animSet1.start();

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
            }
        });
        return view;
    }
    private boolean CheckDoctorinDB() {
        DoctorIDString = CheckDoctorID.getEditText().getText().toString().trim();
        Query checkUser = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Doctor").orderByChild("doctorID").equalTo(DoctorIDString);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    CheckDoctorID.setError("DoctorID has been use!");
                    CheckDoctorID.setErrorEnabled(true);
                    x = false;
                } else {
                    CheckDoctorID.setError(null);
                    CheckDoctorID.setErrorEnabled(false);
                    x = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        return x;
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