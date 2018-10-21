package com.paytel.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.paytel.global_objects;
import com.paytel.home;

import com.paytel.R;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.design.widget.TextInputEditText;

public class accountsettings extends AppCompatActivity {
    userDataObject current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accountsettings);
        current_user = ((global_objects) getApplication()).getCurrent_user();
        display_userinfo();

        Button btn_SAVE_userinfo = findViewById(R.id.btn_save_userinfo);

        btn_SAVE_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_userinfo();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((global_objects) getApplication()).getDynamoDBMapper().save(current_user);
                            // Item updated
                        }
                    }).start();
                try {
                        Intent k = new Intent(accountsettings.this, home.class);
                        startActivity(k);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    void edit_userinfo(){
        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout phone_number = findViewById(R.id.txt_phone_number);
        TextInputLayout city = findViewById(R.id.txt_city);
        TextInputLayout name_on_card = findViewById(R.id.txt_name_on_card);
        TextInputLayout card_number = findViewById(R.id.txt_card_number);
        TextInputLayout CVC = findViewById(R.id.txt_cvc);
        TextInputLayout exp_date = findViewById(R.id.txt_exp_date);

        Map<String, String> cc = new HashMap<String, String>();
        cc.put("name_on_card", name_on_card.getEditText().getText().toString().trim());
        cc.put("card_number", card_number.getEditText().getText().toString().trim());
        cc.put("cvc", CVC.getEditText().getText().toString().trim());
        cc.put("expiration_date", exp_date.getEditText().getText().toString().trim());
        if(f_name != null)current_user.setFirstName(f_name.getEditText().getText().toString().trim());
        if(l_name != null)current_user.setLastName(l_name.getEditText().getText().toString().trim());
        if(street != null)current_user.setStreet(street.getEditText().getText().toString().trim());
        if(city != null)current_user.setCity(city.getEditText().getText().toString().trim());
        if(zip != null)current_user.setZipCode(zip.getEditText().getText().toString().trim());
        if(phone_number != null)current_user.setPhoneNumber(phone_number.getEditText().getText().toString().trim());
        if(cc != null)current_user.setCreditCard(cc);

    }

    void display_userinfo(){
        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout phone_number = findViewById(R.id.txt_phone_number);
        TextInputLayout city = findViewById(R.id.txt_city);
        TextInputLayout name_on_card = findViewById(R.id.txt_name_on_card);
        TextInputLayout card_number = findViewById(R.id.txt_card_number);
        TextInputLayout CVC = findViewById(R.id.txt_cvc);
        TextInputLayout exp_date = findViewById(R.id.txt_exp_date);

        f_name.getEditText().setText(current_user.getFirstName());
        l_name.getEditText().setText(current_user.getLastName());
        street.getEditText().setText(current_user.getStreet());
        zip.getEditText().setText(current_user.getZipCode());
        phone_number.getEditText().setText(current_user.getPhoneNumber());
        city.getEditText().setText(current_user.getCity());
        name_on_card.getEditText().setText(current_user.getCreditCard().get("name_on_card"));
        card_number.getEditText().setText(current_user.getCreditCard().get("card_number"));
        CVC.getEditText().setText(current_user.getCreditCard().get("cvc"));
        exp_date.getEditText().setText(current_user.getCreditCard().get("expiration_date"));

    }
}