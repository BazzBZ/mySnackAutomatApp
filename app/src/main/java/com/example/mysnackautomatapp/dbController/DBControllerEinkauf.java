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

public class DBControllerEinkauf extends SQLiteOpenHelper {
    private static final String tablename = "tblEinkauf"; // tablename
    private static final String kaufID = "kaufID"; // auto generated ID column
    private static final String productID = "productID"; // column name
    private static final String amount = "amount"; // column name
    private static final String price = "price";
    private static final String buyDate = "buyDate";
    private static final String databasename = "dbProducts"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database

    public DBControllerEinkauf(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBControllerEinkauf(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBControllerEinkauf(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + kaufID + " integer primary key, "
                + productID + " integer, " + amount + "  text," + price + "  text," + buyDate + "  text )";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getBuy() {

        ArrayList<HashMap<String, String>> buyList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tablename ,null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("kaufID", cursor.getString(0));
                map.put("productID", cursor.getString(1));
                map.put("amount", cursor.getString(2));
                map.put("price", cursor.getString(3));
                map.put("buyDate", cursor.getString(4));
                buyList.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

// return contact list
        return buyList;
    }

    public boolean addProduct(String productAmount, String productPrice, String productBuyDate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(amount, productAmount);
            cv.put(price, productPrice);
            cv.put(buyDate, productBuyDate);
            db.insert(tablename, null, cv);
            db.close();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
