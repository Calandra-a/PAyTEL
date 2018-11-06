package com.paytel.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.paytel.R;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
//screen that shows up after transaction facial
public class transaction_facial_success extends AppCompatActivity {

    TransactionDataObject new_transaction;
    apicall_transaction aat;
    ApiResponse response;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_facial_success);

        final Button btn_COMPLETE = findViewById(R.id.btn_complete);
        btn_COMPLETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(transaction_facial_success.this, home.class);//progress to home
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}