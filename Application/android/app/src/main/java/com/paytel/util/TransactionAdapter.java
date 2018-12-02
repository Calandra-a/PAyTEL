package com.paytel.util;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;

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
        try {
            TransactionCard currentTrans = transactionList.get(position);

            TextView username = (TextView) listItem.findViewById(R.id.txt_user);
            TextView amount = (TextView) listItem.findViewById(R.id.label);
            if (currentTrans.getmSeller().equals(currentTrans.getmUsername())) {
                username.setText(currentTrans.getmBuyer());
                amount.setText("+" + currentTrans.getmAmount());
                amount.setTextColor(ContextCompat.getColor(mContext, R.color.text_money_receive));
            } else {
                username.setText(currentTrans.getmSeller());
                amount.setText("-" + currentTrans.getmAmount());
                amount.setTextColor(ContextCompat.getColor(mContext, R.color.text_money_send));
            }

            TextView transactionID = (TextView) listItem.findViewById(R.id.txt_invisID);
            transactionID.setText(currentTrans.getmTransactionID());
            transactionID.setVisibility(View.INVISIBLE);

            TextView emoji = (TextView) listItem.findViewById(R.id.txt_emoji);
            switch (currentTrans.getmStatus()) {
                case "Confirmed":
                    emoji.setText("✅");
                    break;
                case "Cancelled":
                    emoji.setText("❌");
                    break;
                case "Pending":
                    emoji.setText("⚡");
                    break;
                case "flagged":
                    emoji.setText("\uD83D\uDEA9");
                    break;
                default:
                    emoji.setText("⚡");
                    break;
            }
        }
        catch(Exception e){
        }
        return listItem;
    }
}
