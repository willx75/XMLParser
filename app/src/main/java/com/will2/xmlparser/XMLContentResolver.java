package com.will2.xmlparser;

import android.content.ContentResolver;
import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;

public class XMLContentResolver {

    private ContentResolver contentResolver;
    private BaseXML baseXML;
    private static final String TAG = "Insertion";

    public XMLContentResolver(Context context){
        contentResolver = this.contentResolver;
    }

    public final static String TABLE_FEED = "Feed";
    public final static String COLUMN_ID = "id";
    public final static String COLUMN_URL = "url";


    SQLiteDatabase sqLiteDatabase;

    private final static String authority = "fr.will.xmlparser";
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int FEED_ID = 1;


    static {
        matcher.addURI(authority, "Feed", FEED_ID);
    }


    public void removeData(String nom){

    }

    public void addData(String nom){

    }


}
