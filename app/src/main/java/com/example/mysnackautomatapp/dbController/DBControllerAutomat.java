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

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBControllerAutomat extends SQLiteOpenHelper {
    private static final String tablename = "tblAutomatProducts"; // tablename
    private static final String productID = "productID"; // auto generated ID column
    private static final String amount = "amount"; // column name
    private static final String sellPrice = "sellPrice"; // column name
    private static final String closestMHD = "closestMHD"; // column name
    private static final String databasename = "dbProducts"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database

    public DBControllerAutomat(Context context){
        super(context,databasename,null,1);
    }


    public DBControllerAutomat(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBControllerAutomat(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + amount + " text, " + sellPrice + " text, " + closestMHD + "  text, "
                + productID + " integer primary key, foreign key(productID) references tblProducts(id))";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getAutomatProducts() {

        ArrayList<HashMap<String, String>> productAutomatList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tablename ,null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("productID", cursor.getString(0));
                map.put("amount", cursor.getString(1));
                map.put("sellPrice", cursor.getString(2));
                map.put("closestMHD", cursor.getString(3));
                productAutomatList.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

// return contact list
        return productAutomatList;
    }

    public boolean addAutomatProduct(String productAmount, String productPrice, String productMHD) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(amount, productAmount);
            cv.put(sellPrice, productPrice);
            cv.put(closestMHD, productMHD);
            db.insert(tablename, null, cv);
            db.close();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
