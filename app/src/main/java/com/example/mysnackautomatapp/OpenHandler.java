package com.example.mysnackautomatapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class OpenHandler extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "mySnackApp.db";
    private static final int DATABASE_VERSION = 1;


    private static final String _ID = "_id";
    public static final String TABLE_NAME_SNACK_APP = "mySnackAPP";







    public OpenHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
