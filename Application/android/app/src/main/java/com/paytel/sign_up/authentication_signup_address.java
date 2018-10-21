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

//Alex Dapoz
//I created this screen to make the signup process cleaner
//TODO: logic


public class authentication_signup_address extends AppCompatActivity {

    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication_signup_address);

        new_user = ((global_objects) getApplication()).getNew_user();

        //user data
        Button btn_NEXT_userdata = findViewById(R.id.btn_next_address);

        btn_NEXT_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                boolean next = add_address();
                if (next == true) {
                    try {
                        Intent k = new Intent(authentication_signup_address.this, authentication_signup_facial.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    boolean add_address(){

        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_LONG;

        TextInputLayout street = findViewById(R.id.txt_street);
        TextInputLayout zip = findViewById(R.id.txt_zipcode);
        TextInputLayout city = findViewById(R.id.txt_city);
        TextInputLayout state = findViewById(R.id.txt_state);

        if(street.getEditText().getText().toString().length() == 0 || zip.getEditText().getText().toString().length() ==0 ||
                city.getEditText().getText().toString().length() == 0 /*|| state.getEditText().getText().toString().length() == 0*/){

            CharSequence fail = "No field can be left blank";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(street.getEditText().getText().toString().length() >=50){
            CharSequence fail = "Street address must be under 50 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }

        else if(city.getEditText().getText().toString().length() >=50){
            CharSequence fail = "City must be under 50 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(zip.getEditText().getText().toString().length() != 5){
            CharSequence fail = "Zip code must be 5 digits";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(state.getEditText().getText().toString().length() != 2){
            CharSequence fail = "State must be 2 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else {
            CharSequence succ = "Success";
            Toast toast = Toast.makeText(context, succ, dShort);
            toast.show();
            new_user.setStreet(street.getEditText().getText().toString());
            new_user.setCity(city.getEditText().getText().toString());
            new_user.setZipCode(zip.getEditText().getText().toString());
            new_user.setState(state.getEditText().getText().toString()); 
            return true;
        }
    }
}
