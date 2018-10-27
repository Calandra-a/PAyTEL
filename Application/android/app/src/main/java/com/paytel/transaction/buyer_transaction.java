package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.api.idyonkpcbig0.UsertransactionMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class buyer_transaction extends AppCompatActivity {

    TransactionDataObject new_transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_buyer);
        new_transaction = ((global_objects) getApplication()).getNew_transaction();
        //LOAD DB HERE load_transactioninfo();

        //credit card
        Button btn_approve = findViewById(R.id.btn_approve);
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
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