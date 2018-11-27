package com.paytel.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

public class settings_bankinfo extends AppCompatActivity {
    userDataObject current_user;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_bankinfo);
        current_user = ((global_objects) getApplication()).getCurrent_user();
        display_userinfo();

        Button btn_SAVE_userinfo = findViewById(R.id.btn_save_userinfo);

        btn_SAVE_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                boolean next = edit_userinfo();
                if (next == true) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((global_objects) getApplication()).getDynamoDBMapper().save(current_user);
                            // Item updated
                        }
                    }).start();
                    try {
                        Intent k = new Intent(settings_bankinfo.this, settings_main.class);
                        startActivity(k);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Payment Method");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),settings_main.class));
                finish();
            }
        });
    }
    boolean edit_userinfo(){

        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_LONG;

        TextInputLayout name_on_card = findViewById(R.id.txt_name_on_card);
        MaskEditText card_number = findViewById(R.id.txt_card_number);
        TextInputLayout CVC = findViewById(R.id.txt_cvc);
        MaskEditText exp_date = findViewById(R.id.txt_exp_date);

        if(name_on_card.getEditText().getText().toString().length() == 0 || card_number.getRawText().length() == 0 ||
                CVC.getEditText().getText().toString().length() == 0 || exp_date.getRawText().length() == 0){
            CharSequence fail = "No field can be left blank";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }

        //handle bad expiration date format
        if (!exp_date.getText().toString().matches("\\d{2}/\\d{2}")){
            CharSequence fail = "Expiration date must be mm/yy format";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        //parses month and year for date check
        String date[]= exp_date.getText().toString().split("/");
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);

        if(name_on_card.getEditText().getText().toString().length() >= 50){
            CharSequence fail = "Name on card must be less than 50 characters";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if (card_number.getRawText().length() != 16){
            CharSequence fail = "Card number must be 16 digits";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(CVC.getEditText().getText().toString().length() != 3){
            CharSequence fail = "CVC must be 3 digits";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(month > 12 || month < 1){
            CharSequence fail = "Month must be between 1 and 12";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(year < 18 ){
            CharSequence fail = "Year must be 18 or later ";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else{
            Map<String, String> cc = new HashMap<String, String>();
            cc.put("name_on_card", name_on_card.getEditText().getText().toString().trim());
            cc.put("card_number", card_number.getRawText().trim());
            cc.put("cvc", CVC.getEditText().getText().toString().trim());
            cc.put("expiration_date", exp_date.getRawText().trim());
            if(cc != null)current_user.setCreditCard(cc);
            return true;
        }
    }

    void display_userinfo(){
        TextInputLayout name_on_card = findViewById(R.id.txt_name_on_card);
        MaskEditText card_number = findViewById(R.id.txt_card_number);
        TextInputLayout CVC = findViewById(R.id.txt_cvc);
        MaskEditText exp_date = findViewById(R.id.txt_exp_date);

        name_on_card.getEditText().setText(current_user.getCreditCard().get("name_on_card"));
        card_number.setText(current_user.getCreditCard().get("card_number"));
        CVC.getEditText().setText(current_user.getCreditCard().get("cvc"));
        exp_date.setText(current_user.getCreditCard().get("expiration_date"));
    }
}