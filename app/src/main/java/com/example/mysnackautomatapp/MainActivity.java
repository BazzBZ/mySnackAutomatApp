package com.example.mysnackautomatapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysnackautomatapp.database.EntityDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
    }

    private EntityDBHelper entityDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddEntryFragment addEntryFragment = new AddEntryFragment();
        LagerViewFragment lagerViewFragment = new LagerViewFragment();
        AutomatViewFragment automatViewFragment = new AutomatViewFragment();
        PhotoViewFragment photoViewFragment = new PhotoViewFragment();
        dataManager = new DataManager();
        entityDBHelper = new EntityDBHelper(this);
        //ShowList showList;

        BottomNavigationView bottomNavBar = findViewById(R.id.bottom_navigation_menu);
        bottomNavBar.setOnItemSelectedListener(item -> {
                    if (item.getItemId() == R.id.page_1) {
                        Log.i("Main Activity", "Page 1");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, addEntryFragment)
                                .commit();
                        return true;

                    } else if (item.getItemId() == R.id.page_2) {
                        Log.i("View List", "Page 2");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, lagerViewFragment)
                                .commit();
                        return true;
                    } else if (item.getItemId() == R.id.page_3) {
                        Log.i("Einkauf", "Page 3");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, automatViewFragment)
                                .commit();
                        return true;

                    } else if (item.getItemId() == R.id.page_4) {
                        Log.i("Take photo", "Page 4");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, photoViewFragment)
                                .commit();
                        return true;


                    }

                    return false;
                }
        );
    }

    private DataManager dataManager;

    public DataManager getDataManager() {
        return dataManager;
    }


}
