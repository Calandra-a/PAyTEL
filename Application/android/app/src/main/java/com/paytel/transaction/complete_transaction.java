package com.paytel.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
//useless
public class complete_transaction extends AppCompatActivity{

    TransactionDataObject new_transaction;
    apicall_transaction aat;
    ApiResponse response;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.approvedeny_transaction);
        new_transaction = ((global_objects) getApplication()).getNew_transaction();

        ImageButton btn_deny = findViewById(R.id.btn_cancel);
        btn_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD DENY TO DB STUFF
                //move to next frame

                try {
                    Intent k = new Intent(complete_transaction.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ImageButton btn_approve = findViewById(R.id.btn_check);
        //APPROVE TRANSACTION
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(complete_transaction.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        WaitTask myTask = new complete_transaction.WaitTask();
        myTask.execute();

    }
    class WaitTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            aat = new apicall_transaction();
        }

        protected Boolean doInBackground(Void... params) {
            String transactionID =  ((global_objects) getApplication()).getCurrent_transaction().getTransactionId();
            response = aat.callCloudLogic(transactionID, "confirm", "facial");
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if(response.getStatusCode() == 200){
                try {
                    Intent k = new Intent(complete_transaction.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Intent k = new Intent(complete_transaction.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Log.d("transaction", response.getStatusCode() + " " + response.getStatusText());

        }
    }
}
