package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.TransactionDataObject;

public class buyer_transaction extends AppCompatActivity {

    TransactionDataObject new_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_buyer);
        new_transaction = ((global_objects) getApplication()).getNew_transaction();

        //credit card
        Button btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                load_transactioninfo();
                try {
                    Intent k = new Intent(buyer_transaction.this, authentication_transaction_facial.class);
                    startActivity(k);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void load_transactioninfo(){
        TextView sellerID = findViewById(R.id.txt_sellerID);
        TextView amount = findViewById(R.id.txt_amount);
        TextView note = findViewById(R.id.txt_note);

        sellerID.setText(new_transaction.getSellerId());
        amount.setText(new_transaction.getAmount());
        note.setText(new_transaction.getAmount());
    }
}