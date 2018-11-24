package com.paytel.util;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paytel.R;

import java.util.ArrayList;
import java.util.List;


public class TransactionAdapter extends ArrayAdapter<TransactionCard> {

    private Context mContext;
    private List<TransactionCard> transactionList = new ArrayList<>();

    public TransactionAdapter(@NonNull Context context,  ArrayList<TransactionCard> list) {
        super(context, 0 , list);
        mContext = context;
        transactionList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_listview,parent,false);

        TransactionCard currentTrans = transactionList.get(position);

        TextView username = (TextView) listItem.findViewById(R.id.txt_user);
        username.setText(currentTrans.getmUsername());

        TextView transactionID = (TextView) listItem.findViewById(R.id.txt_invisID);
        transactionID.setText(currentTrans.getmTransactionID());
        transactionID.setVisibility(View.INVISIBLE);

        TextView amount = (TextView) listItem.findViewById(R.id.label);
        amount.setText(currentTrans.getmAmount());

        return listItem;
    }
}
