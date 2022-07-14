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

public class DBControllerVerkauf extends SQLiteOpenHelper {

    private static final String tablename = "tblVerkauf"; // tablename
    private static final String verkaufID = "verkaufID"; // auto generated ID column
    private static final String productID = "productID"; // column name
    private static final String amount = "amount"; // column name
    private static final String price = "price";
    private static final String databasename = "dbProducts"; // Dtabasename
    private static final int versioncode = 1; //versioncode of the database


    public DBControllerVerkauf(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBControllerVerkauf(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBControllerVerkauf(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    public DBControllerVerkauf(Context context) {
        super(context,databasename,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + verkaufID + " integerer primary key, " + amount + " text, "
                + price + "  text, " + productID + " integer, foreign key(productID) references tblProducts(id))";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getBuy() {

        ArrayList<HashMap<String, String>> sellList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tablename ,null);
        if (cursor.moveToFirst()) {
            do {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("verkaufID", cursor.getString(0));
                map.put("amount", cursor.getString(1));
                map.put("price", cursor.getString(2));
                map.put("productID", cursor.getString(3));
                sellList.add(map);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

// return contact list
        return sellList;
    }

    public boolean addProduct(String productAmount, String productPrice, String productBuyDate) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(amount, productAmount);
            cv.put(price, productPrice);
            db.insert(tablename, null, cv);
            db.close();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
