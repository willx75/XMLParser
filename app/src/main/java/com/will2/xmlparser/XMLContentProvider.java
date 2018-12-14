package com.will2.xmlparser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static android.os.Build.ID;

public class XMLContentProvider extends ContentProvider {


    private static final String TAG = "INSERTION";
    private static String authority = "fr.will.xmlparser";
    private BaseXML baseXML;
    SQLiteDatabase sqLiteDatabase;

    public static final String FEED_PATH = "XMLParser";
    private static final int FEED_TABLE = 1;
    private static final int FEED_LINK = 2;
    private static final int FEED_DESCRIPTION = 3;
    private static final int FEED_TITLE = 4;


    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        matcher.addURI(authority, FEED_PATH, FEED_TABLE);
        matcher.addURI(authority, FEED_PATH, FEED_LINK);
        matcher.addURI(authority, FEED_PATH, FEED_DESCRIPTION);
        matcher.addURI(authority, FEED_PATH, FEED_TITLE);

    }

    public XMLContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = baseXML.getWritableDatabase();

        int code = matcher.match(uri);
        int i;
        long id = 0;
        String path;

        Log.d(TAG, "delete uri = " + uri.toString());

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqLiteDatabase = baseXML.getWritableDatabase();
        int code = matcher.match(uri);
        Log.d(TAG, "Uri" + uri.toString());
        long id;
        String path;

        switch (code) {

            case FEED_LINK:
                id = sqLiteDatabase.insert("feed_table", null, values);
                path = FEED_PATH;
                break;

            case FEED_DESCRIPTION:
                id = sqLiteDatabase.insert("feed_table", null, values);
                path = FEED_PATH;
                break;

            case FEED_TITLE:
                id = sqLiteDatabase.insert("feed_table", null, values);
                path = FEED_PATH;
                break;

            default:
                throw new UnsupportedOperationException("Failed to insert ");


        }
        Uri.Builder builder = (new Uri.Builder()).authority(authority).appendPath(path);

        return ContentUris.appendId(builder, id).build();
    }

    @Override
    public boolean onCreate() {
        baseXML.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        sqLiteDatabase = baseXML.getReadableDatabase();
        int code = matcher.match(uri);
        Cursor cursor;
        switch (code) {
            case FEED_LINK:
                cursor = baseXML.getReadableDatabase().query("feed_table", projection, selection, selectionArgs, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException("Not yet implemented");

        }


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {


        long id = matcher.match(uri);

        String path;
        sqLiteDatabase = baseXML.getWritableDatabase();

        try {
            if (id < 0) {
                int cursor = sqLiteDatabase.update(String.valueOf(XMLContentProvider.FEED_TABLE), values, selection, selectionArgs);
            } else {
                int cursor = sqLiteDatabase.update(String.valueOf(FEED_TABLE), values, BaseXML.COLUMN_ID + " =" + id, null);
            }

        } finally {
            baseXML.close();
        }

        throw new UnsupportedOperationException("Not yet implemented");


    }


}
