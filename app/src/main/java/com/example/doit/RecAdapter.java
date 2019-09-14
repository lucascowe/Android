package com.example.doit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    public ArrayList<GeneralRecyclerList> recyclerList;

    public RecAdapter(ArrayList<GeneralRecyclerList> list) {
        this.recyclerList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView header, comment, data3, data4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.tvHeader);
            comment = itemView.findViewById(R.id.tvComment);
            data3 = itemView.findViewById(R.id.tvData3);
            data4 = itemView.findViewById(R.id.tvData4);
        }
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, int position) {
        holder.header.setText(recyclerList.get(position).getData1());
        holder.comment.setText(recyclerList.get(position).getData2());
        holder.data3.setText(recyclerList.get(position).getData3());
        holder.data4.setText(recyclerList.get(position).getData4());
    }

    @Override
    public int getItemCount() {
        return recyclerList.size();
    }



}
