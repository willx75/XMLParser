package com.will2.xmlparser;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtMain;
    private Button btnSearch;
    private RecyclerView rcvMain;
    private DocumentAdapter documentAdapter ;

    ArrayList<DocumentModel> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //StringAdapter adapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMain = findViewById(R.id.edtMain);
        btnSearch = findViewById(R.id.btnSearch);
        rcvMain = findViewById(R.id.rcvMain);

        documentAdapter = new DocumentAdapter(this);
        rcvMain.setLayoutManager(new LinearLayoutManager(this));
        rcvMain.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rcvMain.setAdapter(documentAdapter);

        //documentAdapter.setItems(items);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtMain.getText().toString())) {
                    Toast.makeText(MainActivity.this, "La chaine est vide", Toast.LENGTH_SHORT).show();
                } else
                    startDownload(edtMain.getText().toString());

            }
        });

        registerReceiver(broadcastReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)); // au moment ou le systeme aura une action de download complete alors cela va appeler le BR passée en parametre(fin de telechargement)

    }


    /**
     * @param url fonction qui demarre le telechargement
     *            prend en argument une url
     *            exception faite si on a mis des lmots
     */
    public void startDownload(String url) throws IllegalArgumentException {
        long idDownload = 0;
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri); // cree la requete de telechargemnt cela va demarrer le dl

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI); // requete pour appliquer le type de telechargement compatible
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE); // pour voir la notification & pourcentage de telechargement
        request.setTitle(getString(R.string.downloading_label));
        request.setVisibleInDownloadsUi(true); // afficher dans le telechargement systeme

        String fileName = uri.getLastPathSegment(); // je recupère la derniere partie de l'url
        request.setDestinationInExternalPublicDir("MPLRSS", fileName);// creation a la racine
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            idDownload = downloadManager.enqueue(request); // ajouter le dl a la file d'attente une fois que le systeme le rend bon de le telecharger(ca marche comme une file)
            Toast.makeText(this, "Installing...", Toast.LENGTH_SHORT).show();
        }

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "download finished", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

