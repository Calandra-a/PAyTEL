package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.hardware.biometrics.*;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.userData;

public class authentication_signup_fingerprint_complete extends AppCompatActivity {
    userData new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authentication_activity_signup_fingerprint);

        new_user = ((global_objects) getApplication()).getNew_user();

        //user data

        //fingerprint
        Button btn_COMPLETE = findViewById(R.id.btn_complete);

        btn_COMPLETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //move to next frame
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ((global_objects) getApplication()).getDynamoDBMapper().save(new_user);
                        // Item saved
                    }
                }).start();
                try {
                    Intent k = new Intent(authentication_signup_fingerprint_complete.this, home.class);
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
