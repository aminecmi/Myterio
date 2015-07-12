package com.amine.myterio.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.amine.myterio.app.model.City;

import java.util.ArrayList;

public class CityDAO {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "cities.db";

    private static final String TABLE_CITIES = "cities";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_CITY_ID = "CityId";

    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NAME = 1;
    private static final int NUM_COL_CITY_ID = 2;
    private static CityDAO instance = null;
    private final CityDB maBaseSQLite;
    private SQLiteDatabase bdd;

    private CityDAO(Context context) {
        maBaseSQLite = new CityDB(context, NOM_BDD, null, VERSION_BDD);
    }

    public static CityDAO getInstance(Context c) {
        if (instance == null) {
            instance = new CityDAO(c);
            return instance;
        } else {
            return instance;
        }
    }

    private void open() {
        bdd = maBaseSQLite.getWritableDatabase();
    }

    private void close() {
        bdd.close();
    }

    public void insertCity(City city) {
        this.open();
        ContentValues values = getContentValues(city);
        bdd.insert(TABLE_CITIES, null, values);
        this.close();
    }

    private ContentValues getContentValues(City city) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, city.getName());
        values.put(COL_CITY_ID, city.getCityIdentifier());
        return values;
    }

    public void updateCity(City city) {
        this.open();
        ContentValues values = getContentValues(city);
        bdd.update(TABLE_CITIES, values, COL_CITY_ID + " = " + city.getCityIdentifier(), null);
        this.close();
    }

    public void deleteCity(City city) {
        this.open();
        bdd.delete(TABLE_CITIES, COL_CITY_ID + " = " + city.getCityIdentifier(), null);
        this.close();
    }

    public City getCity(int identifier) {
        this.open();
        Cursor c = bdd.query(TABLE_CITIES, null, COL_CITY_ID + " = " + identifier, null, null, null, null);
        c.moveToFirst();
        City city = cursorToCity(c);
        c.close();
        this.close();
        return city;
    }


    public City getCity(String name) {
        this.open();
        Cursor c = bdd.query(TABLE_CITIES, null, COL_NAME + " like \"" + name + "\"", null, null, null, null);
        c.moveToFirst();
        City city = cursorToCity(c);
        c.close();
        this.close();
        return city;
    }

    private City cursorToCity(Cursor c) {
        if (c.getCount() == 0) {
            return null;
        } else {
            City city = new City();
            city.setName(c.getString(NUM_COL_NAME));
            city.setCityIdentifier(c.getInt(NUM_COL_CITY_ID));
            return city;
        }
    }

    public ArrayList<City> getAllCities() {
        this.open();
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_CITIES, null);
        ArrayList<City> cities = cursorToListeCities(c);
        this.close();
        return cities;
    }

    private ArrayList<City> cursorToListeCities(Cursor cursor) {
        ArrayList<City> list = new ArrayList<City>();
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    list.add(cursorToCity(cursor));
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        return list;
    }
}
