package com.amine.myterio.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityDB extends SQLiteOpenHelper {

    private static final String TABLE_CITIES = "cities";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_CITY_ID = "CityId";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_CITIES + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT, " +
            COL_CITY_ID + " INTEGER);";

    public CityDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

