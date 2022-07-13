package com.example.mysnackautomatapp.dbController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBControllerProdukt extends SQLiteOpenHelper {
    private static final String tablename = "tblProducts"; // tablename
    private static final String product = "product"; // column name
    private static final String id = "ID"; // auto generated ID column
    private static final String category = "category"; // column name
    private static final String price = "price";
    private static final String databasename = "dbProducts"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database

    public DBControllerProdukt(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBControllerProdukt(Context context) {
        super(context, databasename, null, versioncode);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + id + " integer primary key, "
                + product + " text, " + category + " text, " + price + "  text )";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getProducts() {

        ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tablename ,null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("product", cursor.getString(1));
                map.put("category", cursor.getString(2));
                map.put("price", cursor.getString(3));
                productList.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

// return contact list
        return productList;
    }

    public boolean addProduct(String productName, String productCategory, String productPrice) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(product, productName);
            cv.put(category, productCategory);
            cv.put(price, productPrice);
            db.insert(tablename, null, cv);
            db.close();

            return true;
        } catch (Exception ex) {
            return false;
        }



    }












}
