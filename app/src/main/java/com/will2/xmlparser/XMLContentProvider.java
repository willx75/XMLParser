package com.will2.xmlparser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.will2.xmlparser.BaseXML;

import static android.os.Build.ID;
import static com.will2.xmlparser.FeedColumn.COLUMN_ID;

public class XMLContentProvider extends ContentProvider {


    private static final String TAG = "INSERTION";
    private static final String GAG = "XMLContentProvider";
    private static String authority = "fr.will.xmlparser";
    SQLiteDatabase sqLiteDatabase;
    private  BaseXML baseXML;

    public static final Uri CONTENT_URI = Uri.parse("content://com.will2.xmlparser.xmlcontentprovider");
    public static final String CONTENT_PROVIDER_MIME = "vnd.android.cursor.item/vnd.will2.xmlparser.xmlcontentprovider";
    public static final String FEED_TABLE = "FEED_TABLE";


    public XMLContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        sqLiteDatabase = baseXML.getWritableDatabase();
        int updateID = 0;
        long id = getId(uri);

        String path;
        try {
            if (id > 0) {
                updateID = sqLiteDatabase.delete(FEED_TABLE, selection, selectionArgs);
            } else {

                updateID = sqLiteDatabase.delete(FEED_TABLE, selection, selectionArgs);
            }
        } catch (Exception e) {
            Log.d(TAG, "delete uri = " + uri.toString());
            throw new UnsupportedOperationException("Not yet implemented");

        } finally {
            sqLiteDatabase.close();
        }
        return updateID;
    }


    @Override
    public String getType(Uri uri) {
        return CONTENT_PROVIDER_MIME;
    }


    private long getId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            try {
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {
                Log.e("ContentProviderManager", "Number Format Exception : " + e.getMessage());
            }
        }
        return -1;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqLiteDatabase = baseXML.getWritableDatabase();
        Uri insertUri = null;
        long id = 0;


        try {
            id = sqLiteDatabase.insertOrThrow(FEED_TABLE, null, values);
            Log.d(GAG, "id de l'insertion" + id);

            if (id == -1) {
                throw new RuntimeException("Echec de l'insertion");

            } else {
                insertUri = ContentUris.withAppendedId(uri, id);
            }

        } catch (Exception e) {
            Log.e(GAG, "Insert Exception : " + e.getMessage());

        } finally {
            sqLiteDatabase.close();
        }


        return insertUri;
    }


    @Override
    public boolean onCreate() {
        baseXML = BaseXML.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        long id = getId(uri);

        sqLiteDatabase = baseXML.getReadableDatabase();
        if (id > 0) {
            return sqLiteDatabase.query(FEED_TABLE, projection, selection, selectionArgs, null, null, sortOrder);

        } else {
            return sqLiteDatabase.query(FEED_TABLE, projection, COLUMN_ID + " =" + id, selectionArgs, null, null, null);
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int cursor;
        long id = getId(uri);
        sqLiteDatabase = baseXML.getWritableDatabase();

        try {
            if (id > 0) {
                cursor = sqLiteDatabase.update(XMLContentProvider.FEED_TABLE, values, selection, selectionArgs);
            } else {
                cursor = sqLiteDatabase.update(XMLContentProvider.FEED_TABLE, values, COLUMN_ID + " =" + id, null);
            }
        } catch (Exception e) {
            Log.d(GAG, "Update = " + e.getMessage());

            throw new UnsupportedOperationException("Not yet implemented");

        } finally {
            baseXML.close();
        }


        return cursor;
    }
}