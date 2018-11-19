package com.paytel.sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;

import android.widget.Toast;

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

        //Handle empty field
        if(name_on_card.getEditText().getText().toString().length() == 0 || card_number.getRawText().length() ==0 ||
                CVC.getEditText().getText().toString().length() == 0 || exp_date.getRawText().length() == 0){

            CharSequence fail = "No field can be left blank";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        //handle bad expiration date format
        if (!exp_date.getText().toString().matches("\\d{2}/\\d{2}")){
            CharSequence fail = "Expiration date must be mm/yy format";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        //parses month and year for date check
        String date[]= exp_date.getText().toString().split("/");
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);

        //runs rest of checks that dont need to be independently ran
        if(name_on_card.getEditText().getText().toString().length() >= 50){
            CharSequence fail = "Name on card must be less than 50 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if (card_number.getRawText().length() != 16){
            CharSequence fail = "Card number must be 16 digits"; 
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(CVC.getEditText().getText().toString().length() != 3){
            CharSequence fail = "CVC must be 3 digits";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(month > 12 || month < 1){
            CharSequence fail = "Month must be between 1 and 12";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(year < 18 ){
            CharSequence fail = "Year must be 18 or later ";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;}
        else {
            CharSequence succ = "Success";
            Toast toast = Toast.makeText(context, succ, dShort);
            toast.show();

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

    }
}
