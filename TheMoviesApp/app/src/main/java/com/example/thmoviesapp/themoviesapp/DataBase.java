package com.example.thmoviesapp.themoviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abed Eid on 17/04/2016.
 */
class DataBase extends SQLiteOpenHelper {
    static final String dataBase_Name = "Movies_Favourite";
    static final String table_Name = "Favourite";
    static final int dataBase_Version = 1;
    static final String id = "id";
    static final String URL = "url";


    private static final String Drop_Table = "Drop table if exists " + table_Name;
    private static final String Create_Table = "create table " + table_Name + "(" + id + " Integer, " + URL + " text);";

    private Context context;

    public DataBase(Context context) {
        super(context, dataBase_Name, null, dataBase_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Create_Table);

        } catch (SQLiteException e) {
//            Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Drop_Table);
            onCreate(db);
    }
}
