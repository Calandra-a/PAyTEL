package com.paytel.transaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.userDataObject;

import java.util.ArrayList;
import java.util.Set;

public class start_buyer_transaction extends AppCompatActivity {

    TransactionDataObject current_transaction;
    String transactionID;
    apicall_transaction aat;
    ApiResponse response;

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

        //user actions
        Button btn_approve = findViewById(R.id.btn_approve);
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(start_buyer_transaction.this, transaction_facial.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Button btn_deny = findViewById(R.id.btn_deny);
        btn_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                WaitTask myTask = new start_buyer_transaction.WaitTask();
                myTask.execute();

            }
        });

        //toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.transaction_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending transaction");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
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
                final userDataObject current_user = ((global_objects)getApplication()).getDynamoDBMapper().load(userDataObject.class, IdentityManager.getDefaultIdentityManager().getCachedUserID());
                ((global_objects) getApplication()).setCurrent_user(current_user);

                Set<String> transactionSet = current_user.getTransactions();
                ArrayList<String> dataSet = new ArrayList<>(transactionSet);
                for (int i = 0; i < dataSet.size(); i++) {
                    TransactionDataObject transaction = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, dataSet.get(i));
                    if(transaction.getTransactionId().equals(transactionID)) {
                        current_transaction = transaction;
                        ((global_objects) getApplication()).setCurrent_transaction(current_transaction);
                    }
                }
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //you can add phone number and username here
                            TextView amount = findViewById(R.id.txt_amount);
                            TextView note = findViewById(R.id.txt_note);
                            TextView buyerID = findViewById(R.id.txt_buyerID);
                            TextView user = findViewById(R.id.txt_username);
                            TextView status = findViewById(R.id.txt_status);

                            Button approve = (Button) findViewById(R.id.btn_approve);
                            Button deny = (Button) findViewById(R.id.btn_deny);
                            System.out.println(current_transaction.getTransactionStatus());
                            if(current_transaction.getTransactionStatus().equals("pending")){
                                if(current_transaction.getBuyerUsername().equals(current_user.getUsername())) {
                                    approve.setVisibility(View.VISIBLE);
                                    deny.setVisibility(View.VISIBLE);
                                }
                            }

                            amount.setText("Amount: $" + current_transaction.getAmount());
                            note.setText("Description: " + current_transaction.getNote());
                            buyerID.setText("Buyer ID: " +  current_transaction.getBuyerUsername());
                            status.setText("Status: " + current_transaction.getTransactionStatus());

                        }
                    });
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }).start();
    }
    class WaitTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            aat = new apicall_transaction();
        }

        protected Boolean doInBackground(Void... params) {
            response= aat.callCloudLogic(transactionID, "cancel", "");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            Log.d("transaction", response.getStatusCode() + " " + response.getStatusText());
            try {
                Intent k = new Intent(start_buyer_transaction.this, home.class);
                startActivity(k);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}