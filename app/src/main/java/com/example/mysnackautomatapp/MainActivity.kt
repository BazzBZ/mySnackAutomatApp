package com.example.mysnackautomatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addEntryFragment = AddEntryFragment()
        val showList = ShowList()

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> {
                    Log.i("Main Activity", "Page 1")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, addEntryFragment)
                        .commit()
                    true
                }
                R.id.page_2 -> {
                    Log.i("AddEntry", "Page 2 is shwon")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, showList)
                        .commit()
                    true
                }  else -> {
                false
                }
            }
        }
    }

    private val dataManager = DataManager()

    fun getDataManager(): DataManager{
        return dataManager
    }



}
