package com.will2.xmlparser;

import android.app.Activity;
import android.content.Intent;
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
        View view = LayoutInflater.from(recyclerview.getContext()).inflate(R.layout.items_urls, recyclerview, false);
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
    public void onBindViewHolder(@NonNull SaveURLHolder saveURLHolder, int position) {
        String url = urls.get(position).getContent();
        saveURLHolder.txvLink.setText(url);

    }

    class SaveURLHolder extends RecyclerView.ViewHolder {
        TextView txvLink;

        public SaveURLHolder(View itemView) {
            super(itemView);
            txvLink = itemView.findViewById(R.id.txvLink);


        }
    }

}
