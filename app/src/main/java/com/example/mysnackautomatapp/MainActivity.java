package com.example.mysnackautomatapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddEntryFragment addEntryFragment = new AddEntryFragment();
        dataManager = new DataManager();
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
                        Log.i("Add Entry", "Page 2");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, addEntryFragment)
                                .commit();
                        return true;
                    }
                    return false;
                }


        );


    }

    private DataManager dataManager;

    public DataManager getDataManager(){
        return dataManager;
    }


}
