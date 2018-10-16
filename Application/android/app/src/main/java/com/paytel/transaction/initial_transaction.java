package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
public class initial_transaction extends AppCompatActivity {

    TransactionDataObject new_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_seller);
        ((global_objects) getApplication()).setNew_transaction(new TransactionDataObject());
        new_transaction =((global_objects) getApplication()).getNew_transaction();

        //credit card
        Button btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                add_transactioninfo();

                try {
                    Intent k = new Intent(initial_transaction.this, home.class);
                    startActivity(k);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void add_transactioninfo(){
        TextView sellerID = findViewById(R.id.txt_sellerID);
        TextView amount = findViewById(R.id.txt_amount);
        TextView note = findViewById(R.id.txt_note);

        if(sellerID != null)new_transaction.setSellerId(sellerID.getText().toString());
        if(amount != null)new_transaction.setAmount(amount.getText().toString());
        if(note != null)new_transaction.setNote(note.getText().toString());
    }
}