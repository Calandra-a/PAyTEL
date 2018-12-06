package com.paytel.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;

public class add_funds extends AppCompatActivity {
    userDataObject current_user;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_funds);
        current_user = ((global_objects) getApplication()).getCurrent_user();
        display_wallet();

        Button btn_add_funds = findViewById(R.id.btn_save_funds);

        btn_add_funds.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                boolean next = add_funds();
                if (next == true) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((global_objects) getApplication()).getDynamoDBMapper().save(current_user);
                            // Item updated
                        }
                    }).start();
                    try {
                        Intent k = new Intent(add_funds.this, home.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void display_wallet() {
        TextView current_funds = findViewById(R.id.txt_current_funds);
        current_funds.setText("Wallet: " + Double.toString(current_user.getWallet()));
    }

    boolean add_funds() {

        Context context = getApplicationContext();
        int dLong = Toast.LENGTH_LONG;

        TextInputLayout wallet = findViewById(R.id.txt_wallet);
        boolean fail = false;
        if (wallet.getEditText().getText().toString().length() == 0) {

            wallet.setError("No field can be left blank");
            fail = true;
        } else if (!wallet.getEditText().getText().toString().matches("\\d+([.]\\d{2})?")) {
            if (wallet.getEditText().getText().toString().matches("([.]\\d{2})?")) {
                wallet.setError("Dollar amount should be in 0.00 format");
                fail = true;
            } else {
                wallet.setError("Please revise input try using 0.00 format");
                fail = true;
            }
        } else if ((!wallet.getEditText().getText().toString().contains(".") && wallet.getEditText().getText().toString().length() >= 5) ||
                (wallet.getEditText().getText().toString().contains(".") && wallet.getEditText().getText().toString().length() >= 8)) {

            wallet.setError("Funds added must be under $10000 per addition");
            fail = true;
        } else {
            wallet.setErrorEnabled(false);
        }
        if (fail == false) {
            if (wallet != null) {
                Double currentFunds = current_user.getWallet();
                Double funds = Double.parseDouble(wallet.getEditText().getText().toString().trim());
                current_user.setWallet(funds + currentFunds);
            }
            return true;

        }else {
                return false;
            }

        }
    }

