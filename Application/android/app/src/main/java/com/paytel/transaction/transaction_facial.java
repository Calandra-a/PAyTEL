package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.paytel.R;

public class transaction_facial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.transaction_facial);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, transaction_facial_fragment.newInstance())
                    .commit();
        }

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
