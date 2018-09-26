package com.paytel.sign_up;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userData;

public class authentication_signup_facial  extends AppCompatActivity {
    userData new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authentication_activity_signup_facial);

        new_user = ((global_objects) getApplication()).getNew_user();

        //facial rekognition
        Button btn_NEXT_facial = findViewById(R.id.btn_next_facial);
        btn_NEXT_facial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(authentication_signup_facial.this, authentication_signup_fingerprint_complete.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
