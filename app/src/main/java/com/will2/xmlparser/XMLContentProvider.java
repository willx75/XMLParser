package com.will2.xmlparser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class XMLContentProvider extends ContentProvider {


    private static final String TAG = "INSERTION";
    private static String authority = "fr.will.xmlparser";
    private BaseXML baseXML;
    SQLiteDatabase sqLiteDatabase;
    private static final int FEED_TABLE = 1;
    private static final int FEED_LINK = 2;
    private static final int FEED_DELETE = 3;

    //  private static final int  = 2 ;

    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        matcher.addURI(authority, "feed_table", FEED_TABLE);
        matcher.addURI(authority, "feed_link", FEED_LINK);
        matcher.addURI(authority, "feed_delete", FEED_DELETE);
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
        long id = 0;
        String path;

        switch (code) {

            case FEED_LINK:
                id = sqLiteDatabase.insert("feed_table", null, values);
                path = "feed_link";
                break;

            default:
                throw new UnsupportedOperationException("Not yet implemented");


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
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");






    }




                }
