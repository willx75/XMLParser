package com.will2.xmlparser;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentHolder> {

    private List<DocumentModel> listItems;
    MainActivity activity;


    public DocumentAdapter(MainActivity activity) {
        this.activity = activity;

    }

    public void resetData(List<DocumentModel> items) {
        this.listItems = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int i) {

        View view = LayoutInflater.from(recyclerview.getContext()).inflate(R.layout.items_cell, recyclerview, false);

        DocumentHolder holder = new DocumentHolder(view);


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final DocumentHolder documentHolder, int i) {

        // on recupere l'element a la position i de la liste
        final DocumentModel documentModel = listItems.get(i);

        documentHolder.txvTitle.setText(documentModel.title);
        documentHolder.txvDescription.setText(documentModel.description);
        documentHolder.txvLink.setText(documentModel.link);

        documentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            // permettant a ce que lorsque l'on appuie sur un element de la recyclerview cela puisse afficher et se rabattre
            public void onClick(View v) {
                if(documentHolder.txvLink.getVisibility() == View.VISIBLE){
                    documentHolder.txvLink.setVisibility(View.GONE);
                    documentHolder.txvDescription.setVisibility(View.GONE);
                }else{
                    documentHolder.txvLink.setVisibility(View.VISIBLE);
                    documentHolder.txvDescription.setVisibility(View.VISIBLE);
                }
            }
        });


        documentHolder.txvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(documentModel.link));
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listItems == null) {
            return 0;
        }
        return listItems.size();
    }


    class DocumentHolder extends RecyclerView.ViewHolder {

        public TextView txvTitle;
        public TextView txvDescription;
        public TextView txvLink;


        public DocumentHolder(@NonNull View itemView) {
            super(itemView);
            txvDescription = itemView.findViewById(R.id.txvDescription);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvLink = itemView.findViewById(R.id.txvLink);

        }

    }
}


