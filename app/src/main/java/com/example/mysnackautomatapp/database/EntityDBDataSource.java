package com.example.mysnackautomatapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class EntityDBDataSource {

    private SQLiteDatabase database;
    private Object OpenHandler;

    public EntityDBDataSource(Context context){
        OpenHandler = new EntityDBHelper(context);
    }


}
