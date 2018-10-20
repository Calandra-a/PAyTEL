package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;

public class authentication_signup_facial extends AppCompatActivity {
    userDataObject new_user;

    public String test() {
        return ("Back in the activity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.authentication_activity_signup_facial);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, authentication_signup_facial_fragment.newInstance())
                    .commit();
        }

        new_user = ((global_objects) getApplication()).getNew_user();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ((global_objects) getApplication()).getDynamoDBMapper().save(new_user);
                // Item saved
            }
        }).start();

        //facial rekognition
        Button btn_NEXT_facial = findViewById(R.id.btn_next_facial);
        btn_NEXT_facial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(authentication_signup_facial.this, authentication_signup_complete.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
