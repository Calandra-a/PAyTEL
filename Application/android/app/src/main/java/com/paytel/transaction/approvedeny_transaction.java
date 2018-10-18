package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;

public class approvedeny_transaction extends AppCompatActivity{

    TransactionDataObject new_transaction;


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
                    Intent k = new Intent(approvedeny_transaction.this, home.class);
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
                    Intent k = new Intent(approvedeny_transaction.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
