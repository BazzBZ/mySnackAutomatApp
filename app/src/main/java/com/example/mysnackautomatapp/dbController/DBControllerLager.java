package com.example.mysnackautomatapp.dbController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mysnackautomatapp.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DBControllerLager extends SQLiteOpenHelper {
    private static final String tablenameLager = "tblLagerProducts"; // tablename
    private static final String id = "id"; // tablename
    //private static final String productID = "productID"; // column name
    private static final String name = "name"; // auto generated ID column
    private static final String category = "category"; // column name


    private static final String databasename = "dbProducts"; // Dtabasename
    private static final int versioncode = 6; //versioncode of the database
    //private static final String amount = "amount";



    public DBControllerLager(Context context) {
        super(context, databasename, null, versioncode);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablenameLager + "(" + id + "integer primary key, " +  name + " text, " + category + "  text )";
        database.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablenameLager;
        database.execSQL(query);
        onCreate(database);
    }


    public ArrayList<HashMap<String, String>> getLagerProducts() {

        ArrayList<HashMap<String, String>> productLagerList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tablenameLager ,null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("category", cursor.getString(2));
                productLagerList.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

// return contact list
        return productLagerList;
    }


    public boolean addLagerProduct(String productName, String productCat) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(name, productName);
            cv.put(category, productCat);
            db.insert(tablenameLager, null, cv);
            db.close();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }






    }

