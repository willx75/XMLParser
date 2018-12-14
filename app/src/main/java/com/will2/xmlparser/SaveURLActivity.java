package com.will2.xmlparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class SaveURLActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SaveURLAdapter saveURLAdapter;
    List<Feed> feedList;
    public static String EXTRA ="Url is saved";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_url);
        recyclerView = findViewById(R.id.rcvSaveURL);


        saveURLAdapter = new SaveURLAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(saveURLAdapter);



    }
}
