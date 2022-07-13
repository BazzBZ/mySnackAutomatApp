package com.example.mysnackautomatapp.dbController;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    public DBControllerProdukt(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBControllerProdukt(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
