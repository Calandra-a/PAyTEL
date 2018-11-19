package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;

public class authentication_signup_facial extends AppCompatActivity {
    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_facial);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, authentication_signup_facial_fragment.newInstance())
                    .commit();
        }

        new_user = ((global_objects) getApplication()).getNew_user();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                ((global_objects) getApplication()).getDynamoDBMapper().save(new_user);
                // Item saved
            }
        }).start();*/
    }

    public void pictureComplete() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ((global_objects) getApplication()).getDynamoDBMapper().save(new_user);
                // Item saved
            }
        }).start();
        try {
            Intent k = new Intent(authentication_signup_facial.this, authentication_signup_complete.class);
            startActivity(k);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void pictureIncomplete() {
        try {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, authentication_signup_facial_fragment.newInstance())
                        .commit();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

