package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.api.idyonkpcbig0.UsertransactionMobileHubClient;
import com.paytel.util.userDataObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class buyer_transaction extends AppCompatActivity {

    TransactionDataObject current_transaction;
    String transactionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
            transactionID = bundle.getString("name");

        setContentView(R.layout.transaction_buyer);
        //LOAD DB HERE
        initialize();
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
    void initialize(){
        new Thread(new Runnable() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void run() {
                userDataObject current_user = ((global_objects)getApplication()).getDynamoDBMapper().load(userDataObject.class, IdentityManager.getDefaultIdentityManager().getCachedUserID());
                ((global_objects) getApplication()).setCurrent_user(current_user);

                Set<String> transactionSet = current_user.getTransactions();
                ArrayList<String> dataSet = new ArrayList<>(transactionSet);
                for (int i = 0; i < dataSet.size(); i++) {
                    TransactionDataObject transaction = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, dataSet.get(i));
                    if(transaction.getTransactionId().equals(transactionID))
                        current_transaction = transaction;
                }
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //you can add phone number and username here
                            TextView amount = findViewById(R.id.txt_amount);
                            TextView note = findViewById(R.id.txt_note);
                            TextView buyerID = findViewById(R.id.txt_buyerID);

                            amount.setText("Amount: $" + current_transaction.getAmount());
                            note.setText("Description: " + current_transaction.getNote());
                            buyerID.setText("Buyer ID: " +  current_transaction.getBuyerId());
                        }
                    });
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }).start();
    }

}