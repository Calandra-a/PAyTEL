package com.paytel.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paytel.R;
import com.paytel.home;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

    private ArrayList<TransactionDataObject> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUsername;
        TextView textViewAmount;
        TextView textViewNote;
        TextView textViewStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewUsername = (TextView) itemView.findViewById(R.id.txt_userID);
            this.textViewAmount = (TextView) itemView.findViewById(R.id.txt_amount);
            this.textViewNote = (TextView) itemView.findViewById(R.id.txt_note);
            this.textViewStatus = (TextView) itemView.findViewById(R.id.txt_status);
        }
    }

    public TransactionAdapter(ArrayList<TransactionDataObject> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(home.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewUsername = holder.textViewUsername;
        TextView textViewAmount = holder.textViewAmount;
        TextView textViewNote = holder.textViewNote;
        TextView textViewStatus = holder.textViewStatus;

        textViewUsername.setText(dataSet.get(listPosition).getBuyerId());
        textViewAmount.setText(dataSet.get(listPosition).getAmount());
        textViewNote.setText(dataSet.get(listPosition).getNote());
        textViewStatus.setText(dataSet.get(listPosition).getTransactionStatus());
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}