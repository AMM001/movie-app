package com.example.thmoviesapp.themoviesapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abed Eid on 17/04/2016.
 */
public class DataBaseConnection {
    DataBase Db;
    Context context;

    public DataBaseConnection(Context context) {
        Db = new DataBase(context);
        this.context = context;
    }

    public List<Movie> getmovie(){
        int i = 0;
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        String col[] = { DataBase.id, DataBase.URL};
        Cursor cursor = sqLiteDatabase.query(DataBase.table_Name, col, null, null, null, null, null);
        List<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()){
            String url = cursor.getString(cursor.getColumnIndex(DataBase.URL));
            int id = cursor.getInt(cursor.getColumnIndex(DataBase.id));
            Movie movie = new Movie();
            movie.setURL(url, id);
            movies.add(movie);
        }
        return movies;
    }


    public long insert(int id, String url) {
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBase.URL, url);
        contentValues.put(DataBase.id, id);
        long i = sqLiteDatabase.insert(DataBase.table_Name, null, contentValues);

        return i;

    }

    public int deleteName(int id) {
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();

        String[] nameDelete={id + ""};
        int count= sqLiteDatabase.delete(DataBase.table_Name, DataBase.id + " =? ", nameDelete);
        return count;
    }

    public int search(int id){
        int i = 0;
        SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
        String col[] = {DataBase.id};
        Cursor cursor = sqLiteDatabase.query(DataBase.table_Name, col, DataBase.id + "=?", new String[]{id + ""}, null, null, null);
        while (cursor.moveToNext()){
            i = 1;
        }
        return i;
    }


}
