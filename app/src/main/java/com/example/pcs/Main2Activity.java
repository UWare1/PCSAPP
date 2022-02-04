package com.example.pcs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Main2Activity extends AppCompatActivity {

    //Initialize variable
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_chat));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_history));
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
                Toast.makeText(getApplicationContext()
                        ,"You Clicked" + item.getId()
                        ,Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Display toast
                Toast.makeText(getApplicationContext()
                        ,"You Reselected"+ item.getId()
                        ,Toast.LENGTH_SHORT).show();
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
}