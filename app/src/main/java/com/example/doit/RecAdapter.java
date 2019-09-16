package com.example.doit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    private ArrayList<GeneralRecyclerList> recyclerList;
    private RecListener recListener;


    public RecAdapter(ArrayList<GeneralRecyclerList> list, RecListener listener) {
        this.recyclerList = list;
        this.recListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView header, comment, data3, data4;
        RecListener mRecListener;

        public ViewHolder(@NonNull View itemView, RecListener recListener) {
            super(itemView);

            header = itemView.findViewById(R.id.tvHeader);
            comment = itemView.findViewById(R.id.tvComment);
            data3 = itemView.findViewById(R.id.tvData3);
            data4 = itemView.findViewById(R.id.tvData4);

            this.mRecListener = recListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("position from click","" + getAdapterPosition());
            recListener.onRecClick(getAdapterPosition());
        }
    }

    public interface RecListener {
        void onRecClick(int position);
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view,recListener);
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
