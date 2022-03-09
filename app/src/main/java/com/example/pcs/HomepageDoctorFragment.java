package com.example.pcs;

import static android.app.Activity.RESULT_OK;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageDoctorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    View view;
    FrameLayout FragmentHomePageDoctor;
    LinearLayout Assessment, Assessment2, Assessment3, Assessment4;
    AnimatorSet animSet1;
    ImageSlider imageSlider;
    List<SlideModel> slideModels;
    int NumberOfImage = 4;

    private ImageView AddImage;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    public HomepageDoctorFragment() {
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
     * @return A new instance of fragment HomepageDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageDoctorFragment newInstance(String param1, String param2) {
        HomepageDoctorFragment fragment = new HomepageDoctorFragment();
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
        view = inflater.inflate(R.layout.fragment_homepage_doctor, container, false);
        //ToRecentsHistory = view.findViewById(R.id.ToRecentsHistory);

        FragmentHomePageDoctor = view.findViewById(R.id.FragmentHomePageDoctor);
        AddImage = view.findViewById(R.id.AddImage);
        Assessment = view.findViewById(R.id.Assessment);
        Assessment2 = view.findViewById(R.id.Assessment2);
        Assessment3 = view.findViewById(R.id.Assessment3);
        Assessment4 = view.findViewById(R.id.Assessment4);
        imageSlider = view.findViewById(R.id.slider);

        imageView = view.findViewById(R.id.ImageShow);
        storage = FirebaseStorage.getInstance("gs://pcsapp-5fb3d.appspot.com");
        storageReference = storage.getReference();

        //-----------------------------------------------------
        animSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.fade);
        animSet1.setTarget(FragmentHomePageDoctor);
        animSet1.start();

        //-----------------------------------------------------
        slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.show));
        slideModels.add(new SlideModel(R.drawable.show1));
        slideModels.add(new SlideModel(R.drawable.show2));
        slideModels.add(new SlideModel(R.drawable.show3));

        imageSlider.setImageList(slideModels,false);

        //-------------------------------------------------------
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);*/
                SelectImage();
            }
        });

        //-------------------------------------------------------
        Assessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/GeJCoAj1EtDPrsM86"));
                startActivity(ToBrowser);
            }
        });
        Assessment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToBrowser2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/YW2f6sd2nMysSLrSA"));
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView imageView = view.findViewById(R.id.AddImage);
            imageView.setImageURI(selectedImage);

            slideModels.add(new SlideModel(String.valueOf(selectedImage)));
            NumberOfImage++;
            if (NumberOfImage > 6){
                slideModels.remove(0);
                NumberOfImage--;
            }
            imageSlider.setImageList(slideModels,false);
        }
    }*/


    private void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                //imageView.setImageBitmap(bitmap);
                slideModels.add(new SlideModel(String.valueOf(filePath)));
                NumberOfImage++;
                if (NumberOfImage > 6){
                    slideModels.remove(0);
                    NumberOfImage--;
                }
                imageSlider.setImageList(slideModels,false);
                UploadImage();
            } catch (Exception e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    private void UploadImage() {
        if (filePath != null) {
            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            // StorageReference ref = storageReference.child("ShowAdvertisement/" + "ImageShow 1"); //2..3..4..5..6... <-- NumberImage
            // If (NumberImage > 6){ ImageShow {NumberImage(ตัวที่ 1)} = ImageShow 7 //แทนที่ตัวแรกด้วยตัวที่ 7..8...ไปเรื่อยๆ
            StorageReference ref = storageReference.child("ShowAdvertisement/" + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(mContext,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Please wait a Second!");
                    //progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }


}