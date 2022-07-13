package com.example.mysnackautomatapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class EntityDBHelper extends SQLiteOpenHelper {

    private static final String TAG = EntityDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "mySnackApp.db";
    private static final int DATABASE_VERSION = 1;

    // Name und Attribute der Tabelle "produkt"
    public static final String TABLE_NAME_PRODUKT = "produkt";
    private static final String _ID = "_proukt_id";
    public static final String NAME = "produkt_name";

    // Tabelle Produkt anlegen
    private static final String TABLE_PRODUKT_CREATE =
            "CREATE TABLE " + TABLE_NAME_PRODUKT + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
            + " VARCHAR(255))";

    // Tabelle Produkt löschen
    private static final String TABLE_PRODUKT_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME_PRODUKT;

    public EntityDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(TAG, "Die Tabelle wird mit dem Befehl: " + TABLE_PRODUKT_CREATE + " angelegt.");
            db.execSQL(TABLE_PRODUKT_CREATE);
        } catch (Exception e){
            Log.e(TAG, "Fehler beim ANlegen der Tabelle" + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade der DB von Version " + oldVersion + " zu " + newVersion);
        db.execSQL(TABLE_PRODUKT_DROP);
        onCreate(db);
    }

    public void insert(String name ){
        long rowID = -1;
        try {
            //DB öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad " + db.getPath());
            //die zu speichernden Werte
            ContentValues contentValues = new ContentValues();
            contentValues.put(NAME, name);

        } catch (SQLiteException e){
            Log.e(TAG,"insert", e);
        } finally {
            Log.d(TAG, "insert(): rowID= "+rowID);
        }
    }

   /* void update(long id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        int numUpdated = db.update(TABLE_NAME_PRODUKT, values, _ID + " = ?", new String[]{Long.toString(id)});
    }*/



    public EntityDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d(TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");

    }

    //public OpenHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
      //  super(context, name, factory, version, errorHandler);
    //}

    public EntityDBHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }




}
