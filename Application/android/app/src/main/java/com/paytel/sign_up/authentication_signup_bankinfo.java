package com.paytel.sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;

import java.util.HashMap;
import java.util.Map;

public class authentication_signup_bankinfo  extends AppCompatActivity {
    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_bankinfo);

        new_user = ((global_objects) getApplication()).getNew_user();

        //credit card
        Button btn_NEXT_bankinfo = findViewById(R.id.btn_next_bankinfo);

        btn_NEXT_bankinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                boolean next = add_creditcard();
                if (next == true) {
                    try {
                        Intent k = new Intent(authentication_signup_bankinfo.this, authentication_signup_address.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    boolean add_creditcard(){
        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_SHORT;



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
            new_user.setCreditCard(cc);
            new_user.setRekognitionIds(null);
            new_user.setWallet(100.00);
            return true;
        }
        else{return false;}

    }
}
