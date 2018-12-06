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

        boolean fail = false;


        //Handle empty field
        if(name_on_card.getEditText().getText().toString().length() == 0){
            name_on_card.setError("No field can be left blank");
            fail = true;
        }
        else if(name_on_card.getEditText().getText().toString().length() >= 50){
            name_on_card.setError("Name on card cant be longer than 50 characters");
            fail = true;
        }
        else{
            name_on_card.setErrorEnabled(false);
        }

        if(card_number.getText().toString().length() ==0 ){
            card_number.setError("No field can be left blank");
            fail = true;
        }
        else if (card_number.getRawText().length() != 16){
            card_number.setError("Card number must be 16 digits");
            fail = true;
        }



        if( CVC.getEditText().getText().toString().length() == 0 ){
            CVC.setError("No field can be left blank");
            fail = true;
        }
        else if(CVC.getEditText().getText().toString().length() != 3){
            CVC.setError("CVC must be 3 digits");
            fail = true;
        }
        else{
            CVC.setErrorEnabled(false);

        }
        int month =0;
        int year=0;

        if (exp_date.getText().toString().matches("\\d{2}/\\d{2}")){
            String date[]= exp_date.getText().toString().split("/");
            month = Integer.parseInt(date[0]);
            year = Integer.parseInt(date[1]);
        }

        if( exp_date.getText().toString().length() == 0){
            exp_date.setError("No field can be left blank");
            fail = true;
        }

        else if (!exp_date.getText().toString().matches("\\d{2}/\\d{2}")){
            exp_date.setError("Expiration data must be in mm/yy format");
            fail = true;
        }

        else if(month > 12 || month < 1){
            exp_date.setError("Invalid month");
            fail = true;
        }
        else if(year < 18 ){
            exp_date.setError("Year must be greater than 18");
            fail = true;
        }

        if (fail == false) {

            Map<String, String> cc = new HashMap<String, String>();
            cc.put("name_on_card", name_on_card.getEditText().getText().toString().trim());
            cc.put("card_number", card_number.getRawText().trim());
            cc.put("cvc", CVC.getEditText().getText().toString().trim());
            cc.put("expiration_date", exp_date.getRawText().trim());
            if(cc != null)current_user.setCreditCard(cc);
            return true;
        }
        else{return false;}

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