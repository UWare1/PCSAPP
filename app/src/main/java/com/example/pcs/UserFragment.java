package com.example.pcs;

import static android.content.Context.MODE_PRIVATE;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
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

import java.sql.Struct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    public static final String[] languages = {"Language", "English", "Thai", "Chinese", "Japanese", "Korean", "Arabic", "French", "German", "Portuguese", "Spanish"};
    Spinner spinner;
    View view;
    ConstraintLayout C1;
    FrameLayout FragmentUser;
    TextView FullnameProfile, EmailProfile;
    ImageView ProfileImage;
    Button EditProfile, SecurityProfile, LanguageProfile, SettingProfile, AboutProfile, Logout;
    Dialog dialog;
    boolean IFCHOOSE = false, PLAY = false, On = true;
    int Brightness, resID, resID1;
    Drawable image, image1;
    String ProfileID, UserID, Fullname, BornDB, NationalIDCard, PhoneNumber, AddressDB, Email, ProfileIDImage;

    AnimatorSet animSet1, animSet2, animSet3;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        view = inflater.inflate(R.layout.fragment_user, container, false);
        C1 = view.findViewById(R.id.C1);
        FragmentUser = view.findViewById(R.id.FragmentUser);
        FullnameProfile = view.findViewById(R.id.FullnameProfile);
        EmailProfile = view.findViewById(R.id.EmailProfile);
        ProfileImage = view.findViewById(R.id.ProfileImage);
        EditProfile = view.findViewById(R.id.EditProfile);
        SecurityProfile = view.findViewById(R.id.SecurityProfile);
        LanguageProfile = view.findViewById(R.id.LanguageProfile);
        AboutProfile = view.findViewById(R.id.AboutProfile);
        SettingProfile = view.findViewById(R.id.SettingProfile);
        Logout = view.findViewById(R.id.Logout);
        dialog = new Dialog(getActivity());

        animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
        animSet1.setTarget(FragmentUser);
        animSet1.start();

        //DB Avatar
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> UserDetails = sessionManager.getUserDatailFromSession();
        UserID = UserDetails.get(SessionManager.KEY_USERID);
        Fullname = UserDetails.get(SessionManager.KEY_FULLNAME);
        BornDB = UserDetails.get(SessionManager.KEY_DATE);
        NationalIDCard = UserDetails.get(SessionManager.KEY_NATIONALIDCARD);
        PhoneNumber = UserDetails.get(SessionManager.KEY_PHONENUMBER);
        AddressDB = UserDetails.get(SessionManager.KEY_ADDRESS);
        Email = UserDetails.get(SessionManager.KEY_EMAIL);

        Query CheckHas = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Teacher").child(UserID);
        CheckHas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map map = (Map) snapshot.getValue();
                ProfileID = String.valueOf(map.get("profileID"));
                ProfileIDImage = ProfileID;

                resID = getResources().getIdentifier(ProfileIDImage, "drawable", getActivity().getPackageName());
                image = getResources().getDrawable(resID);
                ProfileImage.setImageDrawable(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FullnameProfile.setText(Fullname);
        EmailProfile.setText(Email);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.change_profile_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView Avatar = dialog.findViewById(R.id.Avatar);
                Button exit = dialog.findViewById(R.id.exit2);
                Button Item1 = dialog.findViewById(R.id.item1), Item2 = dialog.findViewById(R.id.item2), Item3 = dialog.findViewById(R.id.item3),
                        Item4 = dialog.findViewById(R.id.item4), Item5 = dialog.findViewById(R.id.item5), Item6 = dialog.findViewById(R.id.item6),
                        Item7 = dialog.findViewById(R.id.item7), Item8 = dialog.findViewById(R.id.item8), Item9 = dialog.findViewById(R.id.item9),
                        Item10 = dialog.findViewById(R.id.item10), Item11 = dialog.findViewById(R.id.item11), Item12 = dialog.findViewById(R.id.item12),
                        Item13 = dialog.findViewById(R.id.item13), Item14 = dialog.findViewById(R.id.item14), Item15 = dialog.findViewById(R.id.item15),
                        Item16 = dialog.findViewById(R.id.item16), Item17 = dialog.findViewById(R.id.item17), Item18 = dialog.findViewById(R.id.item18),
                        Item19 = dialog.findViewById(R.id.item19), Item20 = dialog.findViewById(R.id.item20), Item21 = dialog.findViewById(R.id.item21),
                        Item22 = dialog.findViewById(R.id.item22), Item23 = dialog.findViewById(R.id.item23);

                View ViewAn = dialog.findViewById(R.id.ViewAnim);
                GridLayout GridAn = dialog.findViewById(R.id.GridAnim);
                animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
                animSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fadeinto);
                animSet3 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fadeinto);
                animSet1.setTarget(Avatar);
                animSet2.setTarget(GridAn);
                animSet3.setTarget(ViewAn);
                animSet1.start();
                animSet2.start();
                animSet3.start();
                animSet2.setStartDelay(300);
                animSet3.setStartDelay(400);
                exit.bringToFront();
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (IFCHOOSE) {
                            FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://pcsapp-5fb3d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                            DatabaseReference reference = rootNode.getReference("Teacher").child(UserID);
                            Map<String, Object> userUpdates = new HashMap<>();
                            userUpdates.put("profileID", ProfileID);
                            reference.updateChildren(userUpdates);

                            resID1 = getResources().getIdentifier(ProfileID, "drawable", getActivity().getPackageName());
                            image = getResources().getDrawable(resID1);
                            ProfileImage.setImageDrawable(image);
                        }
                        dialog.dismiss();
                    }
                });

                Item1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile1";
                        IFCHOOSE = true;
                    }
                });
                Item2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile2";
                        IFCHOOSE = true;
                    }
                });
                Item3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile3";
                        IFCHOOSE = true;
                    }
                });
                Item4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile4";
                        IFCHOOSE = true;
                    }
                });
                Item5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile5";
                        IFCHOOSE = true;
                    }
                });
                Item6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile6";
                        IFCHOOSE = true;
                    }
                });
                Item7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile7";
                        IFCHOOSE = true;
                    }
                });
                Item8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile8";
                        IFCHOOSE = true;
                    }
                });
                Item9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile9";
                        IFCHOOSE = true;
                    }
                });
                Item10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile10";
                        IFCHOOSE = true;
                    }
                });
                Item11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile11";
                        IFCHOOSE = true;
                    }
                });
                Item12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile12";
                        IFCHOOSE = true;
                    }
                });
                Item13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile13";
                        IFCHOOSE = true;
                    }
                });
                Item14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile14";
                        IFCHOOSE = true;
                    }
                });
                Item15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile15";
                        IFCHOOSE = true;
                    }
                });
                Item16.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile16";
                        IFCHOOSE = true;
                    }
                });
                Item17.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile17";
                        IFCHOOSE = true;
                    }
                });
                Item18.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile18";
                        IFCHOOSE = true;
                    }
                });
                Item19.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile19";
                        IFCHOOSE = true;
                    }
                });
                Item20.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile20";
                        IFCHOOSE = true;
                        Toast.makeText(getActivity(), "Choose Success", Toast.LENGTH_SHORT).show();
                    }
                });
                Item21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile21";
                        IFCHOOSE = true;
                        Toast.makeText(getActivity(), "Choose Success", Toast.LENGTH_SHORT).show();
                    }
                });
                Item22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile22";
                        IFCHOOSE = true;
                    }
                });
                Item23.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProfileID = "iconprofile23";
                        IFCHOOSE = true;
                    }
                });

                dialog.show();
            }
        });
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        mContext, R.style.BottomSheetDialogTheme
                );
                final View bottomSheetView = LayoutInflater.from(getActivity())
                        .inflate(
                                R.layout.activity_bottom_sheet2,
                                (LinearLayout) view.findViewById(R.id.BottomSheetContainer2)
                        );
                final TextView Name = bottomSheetView.findViewById(R.id.Name);
                final TextView Born = bottomSheetView.findViewById(R.id.Born);
                final TextView IDCard = bottomSheetView.findViewById(R.id.IDCard);
                final TextView Phone = bottomSheetView.findViewById(R.id.Phone);
                final TextView EmailShow = bottomSheetView.findViewById(R.id.Email);
                final TextView Address = bottomSheetView.findViewById(R.id.Address);
                final ImageView ProfileImageDialog = bottomSheetView.findViewById(R.id.ProfileImageDialog);

                Name.setText(Fullname);
                Born.setText(BornDB);
                IDCard.setText(NationalIDCard);
                Phone.setText(PhoneNumber);
                EmailShow.setText(Email);
                Address.setText(AddressDB);
                ProfileImageDialog.setImageDrawable(image);

                bottomSheetView.findViewById(R.id.SAVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        SecurityProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.security_layout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button exit = dialog.findViewById(R.id.exit);
                Button Understand = dialog.findViewById(R.id.Understand);

                Understand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                exit.bringToFront();
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        LanguageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.language_layout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                spinner = dialog.findViewById(R.id.spinner);
                Button exit = dialog.findViewById(R.id.exit);
                Button Understand = dialog.findViewById(R.id.LanguageButton);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languages);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedLang = adapterView.getItemAtPosition(i).toString();
                        if (selectedLang.equals("Chinese")) {
                        } else if (selectedLang.equals("Thai")) {
                        } else if (selectedLang.equals("English")) {
                        } else if (selectedLang.equals("Japanese")) {
                        } else if (selectedLang.equals("Korean")) {
                        } else if (selectedLang.equals("Arabic")) {
                        } else if (selectedLang.equals("French")) {
                        } else if (selectedLang.equals("German")) {
                        } else if (selectedLang.equals("Portuguese")) {
                        } else if (selectedLang.equals("Spanish")) {
                        } else {
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                Understand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                exit.bringToFront();
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        AboutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.about_layout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button exit = dialog.findViewById(R.id.exit);
                Button Understand = dialog.findViewById(R.id.Understand);

                Understand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                exit.bringToFront();
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        SettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.setting_layout_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button MusicButton = dialog.findViewById(R.id.MusicButton);
                Button exit = dialog.findViewById(R.id.exit);
                SeekBar LightBar = dialog.findViewById(R.id.Lightbar);
                final ContentResolver contentResolver = getActivity().getContentResolver();
                final Window window = getActivity().getWindow();
                try {
                    Brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
                    LightBar.setProgress(Brightness);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                LightBar.setMin(1);
                LightBar.setMax(100);
                LightBar.setKeyProgressIncrement(1);
                /*LightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        Brightness = progress;
                        //android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
                        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, Brightness);
                        WindowManager.LayoutParams layoutParams = window.getAttributes();
                        layoutParams.screenBrightness = Brightness / (float)300;
                        window.setAttributes(layoutParams);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                });*/

                MusicButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (On == true) {
                            MusicButton.setText(mContext.getString(R.string.off));
                            On = false;
                        } else {
                            MusicButton.setText(mContext.getString(R.string.on));
                            On = true;
                        }

                    }
                });

                exit.bringToFront();
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(getActivity());
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}