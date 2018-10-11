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
        TextView f_name = findViewById(R.id.txt_first_name);
        TextView l_name = findViewById(R.id.txt_last_name);
        TextView street = findViewById(R.id.txt_street);
        TextView zip = findViewById(R.id.txt_zipcode);
        TextView phone_number = findViewById(R.id.txt_phone_number);
        TextView city = findViewById(R.id.txt_city);
        TextView name_on_card = findViewById(R.id.txt_name_on_card);
        TextView card_number = findViewById(R.id.txt_card_number);
        TextView CVC = findViewById(R.id.txt_cvc);
        TextView exp_date = findViewById(R.id.txt_exp_date);

        Map<String, String> cc = new HashMap<String, String>();
        cc.put("name_on_card",name_on_card.getText().toString());
        cc.put("card_number", card_number.getText().toString());
        cc.put("cvc", CVC.getText().toString());
        cc.put("expiration_date", exp_date.getText().toString());
        if(f_name != null)current_user.setFirstName(f_name.getText().toString());
        if(l_name != null)current_user.setLastName(l_name.getText().toString());
        if(street != null)current_user.setStreet(street.getText().toString());
        if(city != null)current_user.setCity(city.getText().toString());
        if(zip != null)current_user.setZipCode(zip.getText().toString());
        if(phone_number != null)current_user.setPhoneNumber(phone_number.getText().toString());
        if(cc != null)current_user.setCreditCard(cc);

    }

    void display_userinfo(){
        TextView f_name = findViewById(R.id.txt_first_name);
        TextView l_name = findViewById(R.id.txt_last_name);
        TextView street = findViewById(R.id.txt_street);
        TextView zip = findViewById(R.id.txt_zipcode);
        TextView phone_number = findViewById(R.id.txt_phone_number);
        TextView city = findViewById(R.id.txt_city);
        TextView name_on_card = findViewById(R.id.txt_name_on_card);
        TextView card_number = findViewById(R.id.txt_card_number);
        TextView CVC = findViewById(R.id.txt_cvc);
        TextView exp_date = findViewById(R.id.txt_exp_date);

        f_name.setHint(current_user.getFirstName());
        l_name.setHint(current_user.getLastName());
        street.setHint(current_user.getStreet());
        zip.setHint(current_user.getZipCode());
        phone_number.setHint(current_user.getPhoneNumber());
        city.setHint(current_user.getCity());
        name_on_card.setHint(current_user.getCreditCard().get("name_on_card"));
        card_number.setHint(current_user.getCreditCard().get("card_number"));
        CVC.setHint(current_user.getCreditCard().get("cvc"));
        exp_date.setHint(current_user.getCreditCard().get("expiration_date"));

    }
}