package com.will2.xmlparser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.will2.xmlparser.FeedColumn.COLUMN_DESCRIPTION;
import static com.will2.xmlparser.FeedColumn.COLUMN_ID;
import static com.will2.xmlparser.FeedColumn.COLUMN_TITLE;
import static com.will2.xmlparser.FeedColumn.COLUMN_URL;

public class SaveURLActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText edtRecherche;
    SaveURLAdapter saveURLAdapter;
    private static int REQUEST_URL;
    public static String EXTRA = "Url is saved";
    List<Feed> searchFeed = new ArrayList<>();

    public static void launch(MainActivity activity) {
        activity.startActivityForResult(new Intent(activity, SaveURLActivity.class), REQUEST_URL);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_url);
        recyclerView = findViewById(R.id.rcvSaveURL);
        edtRecherche = findViewById(R.id.edtRecherche);

        saveURLAdapter = new SaveURLAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(saveURLAdapter);



        edtRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence search, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchFeed.clear();
                String search = edtRecherche.getText().toString();
                for(Feed feed : getFeeds()){
                    if(feed.getTitle().contains(search) || feed.getContent().contains(search) || feed.getDescription().contains(search)){
                        searchFeed.add(feed);
                    }
                }

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        saveURLAdapter.setUrls(searchFeed);

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                saveURLAdapter.setUrls(getFeeds());
            }
        });
    }

    private List<Feed> getFeeds(){
        List<Feed> feeds = new ArrayList<>();
        Cursor cursor = getContentResolver().query(XMLContentProvider.CONTENT_URI, null, null, null, null);

        if(cursor == null) return feeds;

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
        feed.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        feed.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
        feed.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        feed.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
        return feed;
    }
}
