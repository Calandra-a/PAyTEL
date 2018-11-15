package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.paytel.R;

public class transaction_facial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.authentication_activity_transaction_facial);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, transaction_facial_fragment.newInstance())
                    .commit();
        }

        //facial rekognition
        /*Button btn_NEXT_facial = findViewById(R.id.btn_next_facial);
        btn_NEXT_facial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent k = new Intent(transaction_facial.this, complete_transaction.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });*/

    }
    public void pictureComplete() {
        try {
            Intent k = new Intent(transaction_facial.this, complete_transaction.class);
            startActivity(k);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pictureIncomplete() {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, transaction_facial_fragment.newInstance())
                    .commit();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
