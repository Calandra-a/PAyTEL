package com.paytel.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
//screen that shows up after transaction facial
public class complete_transaction extends AppCompatActivity{

    TransactionDataObject new_transaction;
    apicall_transaction aat;
    Object[] response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_complete);
        new_transaction = ((global_objects) getApplication()).getNew_transaction();

        Button btn_complete = findViewById(R.id.btn_complete);
        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        protected void onPostExecute(Boolean bool) {//progress to transaction_facial_success.java
            super.onPostExecute(bool);
            String responseData = (String)response[1];
            ApiResponse globalData = (ApiResponse)response[0];
            String text = responseData.substring(12, (responseData.length()-2));

            TextView Title = findViewById(R.id.txt_transaction_complete_title);
            TextView subText = findViewById(R.id.txt_transaction_complete_subtitle);

            subText.setText(text);
            if(globalData.getStatusCode() == 200){
                Title.setText("Success!");
            }else{
                Title.setText("Failed");
            }
            /*if(response.getStatusCode() == 200){
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
            }*/

            //Log.d("transaction", response.getStatusCode() + " " + response.getStatusText());

        }
    }
}
