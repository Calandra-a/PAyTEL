package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userData;

import java.util.HashMap;
import java.util.Map;

public class authentication_signup_bankinfo  extends AppCompatActivity {
    userData new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authentication_activity_signup_bankinfo);

        new_user = ((global_objects) getApplication()).getNew_user();

        //credit card
        Button btn_NEXT_bankinfo = findViewById(R.id.btn_next_bankinfo);
        Button btn_BACK_bankinfo = findViewById(R.id.btn_back_bankinfo);



        btn_NEXT_bankinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_creditcard();
                //move to next frame
                try {
                    Intent k = new Intent(authentication_signup_bankinfo.this, authentication_signup_address.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void add_creditcard(){
        TextInputLayout name_on_card = findViewById(R.id.txt_name_on_card);
        TextInputLayout card_number = findViewById(R.id.txt_card_number);
        TextInputLayout CVC = findViewById(R.id.txt_cvc);
        TextInputLayout exp_date = findViewById(R.id.txt_exp_date);

        Map<String, String> cc = new HashMap<String, String>();
        cc.put("name_on_card",name_on_card.getEditText().getText().toString());
        cc.put("card_number", card_number.getEditText().getText().toString());
        cc.put("cvc", CVC.getEditText().getText().toString());
        cc.put("expiration_date", exp_date.getEditText().getText().toString());

        new_user.setCreditCard(cc);
    }
}
