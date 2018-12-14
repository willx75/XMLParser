package com.will2.xmlparser;

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

        return new DocumentHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DocumentHolder documentHolder, int i) {

        // on recupere l'element a la position i de la liste
        DocumentModel documentModel = listItems.get(i);

        documentHolder.txvTitle.setText(documentModel.title);
        documentHolder.txvDescription.setText(documentModel.description);
        documentHolder.txvLink.setText(documentModel.link);

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


