package com.will2.xmlparser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class SaveURLAdapter extends RecyclerView.Adapter<SaveURLAdapter.SaveURLHolder> {
    private List<Feed> urls;
    private SaveURLActivity activity;

    public SaveURLAdapter(SaveURLActivity saveURLActivity) {
        this.activity = saveURLActivity;
    }

    public void setUrls(List<Feed> urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (urls == null) {
            return 0;
        }
        return urls.size();
    }

    @NonNull
    @Override
    public SaveURLHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int i) {
        View view = LayoutInflater.from(recyclerview.getContext()).inflate(R.layout.items_cell, recyclerview, false);
        final SaveURLHolder saveHolder = new SaveURLAdapter.SaveURLHolder(view);
        saveHolder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int positon = saveHolder.getAdapterPosition();

                Intent intent = new Intent();
                intent.putExtra(SaveURLActivity.EXTRA, urls.get(positon).getContent());
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
        });


        return saveHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SaveURLHolder saveURLHolder, int position) {
        final Feed feed = urls.get(position);
        
        saveURLHolder.txvTitle.setText(feed.getTitle());
        saveURLHolder.txvDescription.setText(feed.getTitle());
        saveURLHolder.txvLink.setText(feed.getContent());

        saveURLHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveURLHolder.txvLink.getVisibility() == View.VISIBLE){
                    saveURLHolder.txvLink.setVisibility(View.GONE);
                    saveURLHolder.txvDescription.setVisibility(View.GONE);
                }else{
                    saveURLHolder.txvLink.setVisibility(View.VISIBLE);
                    saveURLHolder.txvDescription.setVisibility(View.VISIBLE);
                }
            }
        });


        saveURLHolder.txvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(feed.getContent()));
                activity.startActivity(intent);
            }
        });

    }

    class SaveURLHolder extends RecyclerView.ViewHolder {
        public TextView txvTitle;
        public TextView txvDescription;
        public TextView txvLink;


        public SaveURLHolder(@NonNull View itemView) {
            super(itemView);
            txvDescription = itemView.findViewById(R.id.txvDescription);
            txvTitle = itemView.findViewById(R.id.txvTitle);
            txvLink = itemView.findViewById(R.id.txvLink);

        }

    }

}
