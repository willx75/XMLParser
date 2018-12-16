package com.will2.xmlparser;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SaveURLActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SaveURLAdapter saveURLAdapter;
    private static int REQUEST_URL;
    List<Feed> feedList;
    public static String EXTRA = "Url is saved";

    public static void launch(MainActivity activity) {
        activity.startActivityForResult(new Intent(activity, SaveURLActivity.class), REQUEST_URL);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_url);
        recyclerView = findViewById(R.id.rcvSaveURL);


        saveURLAdapter = new SaveURLAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(saveURLAdapter);


        Toast.makeText(this, getFeeds()+"", Toast.LENGTH_SHORT).show();

    }


    private List<Feed> getFeeds(){
        List<Feed> feeds = new ArrayList<>();
        Cursor cursor = getContentResolver().query(XMLContentProvider.CONTENT_URI, null, null, null, null);
        Toast.makeText(this, cursor.getCount()+"", Toast.LENGTH_SHORT).show();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Feed feed = cursorToFeed(cursor);
            feeds.add(feed);
            cursor.moveToNext();
        }
        cursor.close();
        return feeds;

        // cursor1 : ID     URL     DESCR   TITLE
        // cursor2 : ID     URL     DESCR   TITLE

    }

    private Feed cursorToFeed(Cursor cursor) {
        Feed feed = new Feed();
        feed.setId(cursor.getInt(cursor.getColumnIndex(BaseXML.COLUMN_ID)));
        feed.setContent(cursor.getString(cursor.getColumnIndex(BaseXML.COLUMN_URL)));
        feed.setDescription(cursor.getString(cursor.getColumnIndex(BaseXML.COLUMN_DESCRIPTION)));
        feed.setTitle(cursor.getString(cursor.getColumnIndex(BaseXML.COLUMN_TITLE)));
        return feed;
    }
}
