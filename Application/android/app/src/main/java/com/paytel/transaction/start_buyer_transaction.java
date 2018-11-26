package com.paytel.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    Object[] response;
    private Toast toast = null;

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

                Double wallet = ((global_objects) getApplication()).getCurrent_user().getWallet();
                Double amount = Double.parseDouble(current_transaction.getAmount());

                if (wallet > amount) {
                    //move to next frame
                    try {
                        Intent k = new Intent(start_buyer_transaction.this, transaction_facial.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Context context = getApplicationContext();
                    int dShort = Toast.LENGTH_SHORT;

                    CharSequence fail = "Insufficient Funds";
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(context, fail, dShort);
                    toast.show();


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
                            TextView icon = findViewById(R.id.txt_emoji);
                            TextView amount = findViewById(R.id.txt_amount);
                            TextView note = findViewById(R.id.txt_note);
                            TextView buyerID = findViewById(R.id.txt_buyerID);
                            TextView user = findViewById(R.id.txt_username);
                            TextView status = findViewById(R.id.txt_status);

                            Button approve = (Button) findViewById(R.id.btn_approve);
                            Button deny = (Button) findViewById(R.id.btn_deny);
//                            System.out.println(current_transaction.getTransactionStatus());

                                if(current_transaction.getBuyerUsername().equals(current_user.getUsername())) {
                                    if(current_transaction.getTransactionStatus().equals("pending")) {
                                        approve.setVisibility(View.VISIBLE);
                                        deny.setVisibility(View.VISIBLE);
                                    }
                                    buyerID.setText(current_transaction.getSellerUsername() + " requested:");
                                    amount.setText("-$" + current_transaction.getAmount());
                                    amount.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_money_send));
                                }else{
                                    buyerID.setText(current_transaction.getBuyerUsername() + " paid you:");
                                    amount.setText("+$" + current_transaction.getAmount());
                                    amount.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_money_receive));
                                }


                            //int eggplant = 0x1F346;//eggplant emoji
                            //icon.setText(new String(Character.toChars(eggplant)));
                            note.setText("Note: " + current_transaction.getNote());
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
            try {
                Intent k = new Intent(start_buyer_transaction.this, home.class);
                startActivity(k);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void initialSignup(){
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView mCardview = (TextView) findViewById(R.id.info_text);
                    TextView mUsername = (TextView) findViewById(R.id.info_username);

                    Double wallet = ((global_objects) getApplication()).getCurrent_user().getWallet();
                    mCardview.setText("Wallet: $"+Double.toString(wallet));
                    mUsername.setText(((global_objects) getApplication()).getCurrent_user().getUsername());
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}