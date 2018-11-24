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
import com.paytel.home;
import com.paytel.util.accountsettings;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;


public class settings_identity extends AppCompatActivity {
    userDataObject current_user;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_identity);
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
                        Intent k = new Intent(settings_identity.this, settings_main.class);
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
        getSupportActionBar().setTitle("Edit Profile");
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

        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        MaskEditText phone_number = findViewById(R.id.txt_phone_number);


        if(f_name.getEditText().getText().toString().length() == 0 || l_name.getEditText().getText().toString().length() == 0 ||
                phone_number.getRawText().length() == 0 ){
            CharSequence fail = "No field can be left blank";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }

        if (phone_number.getRawText().length() != 10) {
            CharSequence fail = "Phone number must be 10 digits";
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else{
            if(f_name != null)current_user.setFirstName(f_name.getEditText().getText().toString().trim());
            if(l_name != null)current_user.setLastName(l_name.getEditText().getText().toString().trim());
            if(phone_number != null)current_user.setPhoneNumber(phone_number.getRawText().trim());
            return true;
        }
    }

    void display_userinfo(){
        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        MaskEditText phone_number = findViewById(R.id.txt_phone_number);

        f_name.getEditText().setText(current_user.getFirstName());
        l_name.getEditText().setText(current_user.getLastName());
        phone_number.setText(current_user.getPhoneNumber());
    }
}