package com.mynetgear.dord.platypus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexandre Poon on 15.08.25.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "markers_en.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_SAILAWAY = "sailaway";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_PICFILENAME = "picfilename";
    public static final String COLUMN_AMENITIES = "amenities";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_ISO = "iso";
    public static final String COLUMN_ARTICLE = "article";

    public static final String[] COLUMNS = {COLUMN_NAME, COLUMN_CITY, COLUMN_LAT, COLUMN_LNG, COLUMN_RATING, COLUMN_PICFILENAME,
            COLUMN_AMENITIES, COLUMN_DESC, COLUMN_ISO, COLUMN_ARTICLE};
    private static final String CREATE_SQLITEDB = "CREATE TABLE IF NOT EXISTS " + TABLE_SAILAWAY + " (" +
            COLUMN_NAME + " TEXT, " +
            COLUMN_CITY + " TEXT, " +
            COLUMN_LAT + " REAL, " +
            COLUMN_LNG + " REAL, " +
            COLUMN_RATING + " REAL, " +
            COLUMN_PICFILENAME + " TEXT, " +
            COLUMN_AMENITIES + " TEXT, " +
            COLUMN_DESC + " TEXT ," +
            COLUMN_ISO + " TEXT ," +
            COLUMN_ARTICLE + " TEXT " +
            ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQLITEDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAILAWAY);
        onCreate(db);
    }
}
