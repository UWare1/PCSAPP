package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Main2Activity extends AppCompatActivity {

    //Initialize variable
    MeowBottomNavigation bottomNavigation;
    View decorView;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        decorView = getWindow().getDecorView();

        mediaPlayer = MediaPlayer.create(Main2Activity.this, R.raw.bgsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.doctor));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.healthcare));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_homepage));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_noti));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_user));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;
                //Check condition
                switch (item.getId()){
                    case 1:
                        //When id is 1
                        //Initialize chat fragment
                        fragment = new ChatFragment();
                        break;
                    case 2:
                        //When id is 2
                        //Initialize history fragment
                        fragment = new HistoryFragment();
                        break;
                    case 3:
                        //When id is 3
                        //Initialize Homepage fragment
                        fragment = new HomepageFragment();
                        break;
                    case 4:
                        //When id is 4
                        //Initialize Noti fragment
                        fragment = new NotiFragment();
                        break;
                    case 5:
                        //When id is 4
                        //Initialize Noti fragment
                        fragment = new UserFragment();
                        break;

                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        //Set notification count
        bottomNavigation.setCount(1,"10");
        //Set home fragment initially selected
        bottomNavigation.show(3,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Display toast

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Display toast

            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

    }
    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}