package com.will2.xmlparser;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.will2.xmlparser.BaseXML;

import static android.os.Build.ID;
import static com.will2.xmlparser.FeedColumn.COLUMN_ID;

public class XMLContentProvider extends ContentProvider {


    // URI de notre content provider, elle sera utilisé pour accéder au ContentProvider
    public static final Uri CONTENT_URI = Uri.parse("content://com.will2.xmlparser.xmlcontentprovider");

    // Le Mime de notre content provider, la premiére partie est toujours identique
    public static final String CONTENT_PROVIDER_MIME = "vnd.android.cursor.item/vnd.will2.xmlparser.xmlcontentprovider";

    public static final String TABLE_FEED = "FEED";
    private static String DATABASE_NAME = "xmlparser.db";
    private static final int DATABASE_VERSION = 1;


    // Creation d'une inner class qui va gérer la base de donnée locale
    private static class DatabaseHelper extends SQLiteOpenHelper {

        // Commande sql pour la création de la base de données
        private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_FEED + "(\n" +
                ""
                + FeedColumn.COLUMN_ID + " INTEGER primary key autoincrement,\n" +
                ""
                + FeedColumn.COLUMN_URL + " TEXT,\n" +
                ""
                + FeedColumn.COLUMN_TITLE + " TEXT,\n" +
                ""
                + FeedColumn.COLUMN_DESCRIPTION + " TEXT)";


        // Constructeur du SqlManager à partir du context, du nom de la base et de la version
        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
            onCreate(sqLiteDatabase);
        }
    }

    // Creer une variable SqlManager
    private DatabaseHelper dbHelper;


    // instancier le dbHelper dans le OnCreate du ContentProvider
    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }


    // retourne le type de notre ContentProvider,ce qui correspond tout simplement à notre MIME
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return CONTENT_PROVIDER_MIME;
    }

    // Cette méthode nous permet de récupérer l’id de notre Uri
    private long getId(Uri uri){

        //Vu que les uri sont ecrit de cette façon Content://something/id alors je recupère la derniere partie de l'uri pour avoir l'id
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

    // rajoute une valeur dans notre ContentProvider
    @Nullable
    @Override

    /**
     * On commence par récupérer une instance de la base de données en mode ecriture.
     * Puis on insère nos données à l’aide de la méthode insertOrThrow qui retourne l’id de l’insertion dans la base et -1 en cas d’échec de l’insertion.
     * Sans oublier de fermer la connexion à la base de données quelques soit le resultat.
     */
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //récupérer une instance de la base de données en mode ecriture
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Uri insertUri = null;

        try {
            //Insert or Throw retourne l’id de l’insertion dans la base et -1 en cas d’échec de l’insertion.
            long id = sqLiteDatabase.insertOrThrow(TABLE_FEED, null, contentValues);
            Log.d("ContentProviderManager", "Id de l'insertion : "+id);

            if(id == -1){
                throw new RuntimeException("Echec de l'insertion");
            }
            else{
                insertUri = ContentUris.withAppendedId(uri, id);
            }

        }catch (Exception e){
            Log.e("ContentProviderManager", "Insert Exception : " + e.getMessage());
        }finally {
            //toujours fermé la base qu'il y ai une exception ou pas
            sqLiteDatabase.close();
        }

        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        long id = getId(uri);
        int updateId = -1;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        try {
            //Si l’id est supérieur à 0, on supprime l’élément
            if(id > 0){
                updateId = sqLiteDatabase.delete(TABLE_FEED, s, strings);
            }
            //Sinon on essaye de supprimer l’élement par son id
            else{
                updateId = sqLiteDatabase.delete(TABLE_FEED, FeedColumn.COLUMN_ID+"="+id, null);
            }
        }catch (Exception e){
            Log.e("ContentProviderManager", "Delete Exception : " + e.getMessage());
        }finally {
            sqLiteDatabase.close();
        }

        return updateId;
    }

    /**
     *
     * @param uri
     * @param contentValues
     * @param s
     * @param strings
     * @return
     * On récupérer l’id de l’élément
     * Si l’id est supérieur à 0, on met à jour l’élément
     * Sinon on essaye à mettre à jour l’élement par sa valeur.
     * Sans oublier de fermer la base à la fin
     */

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        long id = getId(uri);
        int updateId = -1;

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        try {
            //Si l’id est supérieur à 0, on met à jour l’élément
            if(id > 0){
                updateId = sqLiteDatabase.update(TABLE_FEED, contentValues, s, strings);
            }
            //Sinon on essaye à mettre à jour l’élement par sa valeur
            else{
                updateId = sqLiteDatabase.update(TABLE_FEED, contentValues, FeedColumn.COLUMN_ID+"="+id, null);
            }
        }catch (Exception e){
            Log.e("ContentProviderManager", "Update Exception : " + e.getMessage());
        }finally {
            sqLiteDatabase.close();
        }

        return updateId;
    }


    // récupérer une donnée présente dans notre ContentProvider.
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        long id = getId(uri);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        if(id > 0){
            Cursor cursor = sqLiteDatabase.query(TABLE_FEED, columns, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }else{
            Cursor cursor = sqLiteDatabase.query(TABLE_FEED, columns, null, null, null, null, sortOrder);
            return cursor;
        }
    }
}