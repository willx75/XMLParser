package com.will2.xmlparser;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringHolder> {
    private ArrayList<String> items;

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StringHolder onCreateViewHolder(@NonNull ViewGroup recyclerview, int i) {
        View view = LayoutInflater.from(recyclerview.getContext()).inflate(R.layout.items_cell,recyclerview,false);


        return new StringHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringHolder stringHolder, int i) {
    stringHolder.txv.setText(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class StringHolder extends RecyclerView.ViewHolder {
        TextView txv ;
        public StringHolder(@NonNull View itemView) {
            super(itemView);
            txv = itemView.findViewById(R.id.txvCell);
        }
    }
}
**/