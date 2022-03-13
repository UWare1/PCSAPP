package com.example.pcs;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    View view;
    LinearLayout FragmentHomePage, Assessment, Assessment2, Assessment3, Assessment4;

    AnimatorSet animSet1;

    public HomepageFragment() {
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
     * @return A new instance of fragment HomepageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
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
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        //ToRecentsHistory = view.findViewById(R.id.ToRecentsHistory);

        FragmentHomePage = view.findViewById(R.id.FragmentHomePage);
        Assessment = view.findViewById(R.id.Assessment);
        Assessment2 = view.findViewById(R.id.Assessment2);
        Assessment3 = view.findViewById(R.id.Assessment3);
        Assessment4 = view.findViewById(R.id.Assessment4);
        ImageSlider imageSlider = view.findViewById(R.id.slider);

        //-----------------------------------------------------
        animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
        animSet1.setTarget(FragmentHomePage);
        animSet1.start();

        //-----------------------------------------------------
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.show));
        slideModels.add(new SlideModel(R.drawable.show1));
        slideModels.add(new SlideModel(R.drawable.show2));
        slideModels.add(new SlideModel(R.drawable.show3));

        imageSlider.setImageList(slideModels,false);

        //-------------------------------------------------------
        Assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/YW2f6sd2nMysSLrSA"));
                startActivity(ToBrowser);
            }
        });
        Assessment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/GeJCoAj1EtDPrsM86"));
                startActivity(ToBrowser2);
            }
        });
        Assessment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser3 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/GeJCoAj1EtDPrsM86"));
                startActivity(ToBrowser3);
            }
        });
        Assessment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser4 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/GeJCoAj1EtDPrsM86"));
                startActivity(ToBrowser4);
            }
        });
        return view;
    }
}