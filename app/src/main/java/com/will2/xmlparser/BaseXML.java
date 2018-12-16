package com.will2.xmlparser;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.will2.xmlparser.FeedColumn.COLUMN_DESCRIPTION;
import static com.will2.xmlparser.FeedColumn.COLUMN_ID;
import static com.will2.xmlparser.FeedColumn.COLUMN_TITLE;
import static com.will2.xmlparser.FeedColumn.COLUMN_URL;

class BaseXML extends SQLiteOpenHelper {


    private final static int VERSION = 1;
    public final static String DB_NAME = "XMLParser";
    public final static String TABLE_FEED = "FEED_TABLE";


    public final static String CREATE_FEED = "create table " + TABLE_FEED + "(" +
            COLUMN_ID + "integer not null primary key , " +
            COLUMN_URL + " string," +
            COLUMN_TITLE + "string, " +
            COLUMN_DESCRIPTION + "string " + ")";

    private static BaseXML ourInstance;


    public static BaseXML getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new BaseXML(context);
        return ourInstance;
    }

    public BaseXML(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FEED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists " + TABLE_FEED);
            onCreate(db);
        }
    }


}
