package com.paytel.sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;
import com.santalu.widget.MaskEditText;


public class authentication_signup_identity extends AppCompatActivity {
    userDataObject new_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_identity);

        ((global_objects) getApplication()).setNew_user(new userDataObject());
        new_user = ((global_objects) getApplication()).getNew_user();

        //user data
        Button btn_NEXT_userdata = findViewById(R.id.btn_next_identity);

        btn_NEXT_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                boolean next = add_userinfo();
                if (next == true){
                    check_username(new_user.getUsername().toLowerCase());
                }
            }

        });


    }

    boolean add_userinfo() {

        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_SHORT;

        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        TextInputLayout user_name = findViewById(R.id.txt_username);
        MaskEditText phone_number = findViewById(R.id.txt_phone_number);


        new_user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());



       /* if (user_name.getEditText().getText().toString().length() == 0 || f_name.getEditText().getText().toString().length() == 0 ||
                l_name.getEditText().getText().toString().length() == 0 || phone_number.getRawText().length() == 0) {
            CharSequence fail = "No field can be left blank";

        }*/
       boolean fail = false;
       if(user_name.getEditText().getText().toString().length() == 0 ) {
           user_name.setError("No field can be left blank");
           fail = true;
       }
        else if (user_name.getEditText().getText().toString().length() > 20) {
            user_name.setError("Username cant be over 20 characters");
            fail = true;
        }
        else {user_name.setErrorEnabled(false);}



        if(f_name.getEditText().getText().toString().length() == 0 ) {
           f_name.setError("No field can be left blank");
           fail = true;
       }
        else {f_name.setErrorEnabled(false);}




        if(l_name.getEditText().getText().toString().length() == 0 ) {
           l_name.setError("No field can be left blank");
           fail = true;
       }
        else {l_name.setErrorEnabled(false);}

        if (phone_number.getText().toString().length() == 0) {
            phone_number.setError("No field can be left blank");
            fail = true;
        }
        else if (phone_number.getText().toString().length() != 14) {
            phone_number.setError("Invalid Phone Number");
            fail = true;
        }


        if(fail == false) {
            new_user.setUsername(user_name.getEditText().getText().toString().trim().toLowerCase());
            new_user.setFirstName(f_name.getEditText().getText().toString().trim());
            new_user.setLastName(l_name.getEditText().getText().toString().trim());
            new_user.setPhoneNumber(phone_number.getRawText().trim());
            return true;
        }
        else {return false;}

    }


    void check_username(final String username ){

        new Thread(new Runnable() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void run() {
                userDataObject user = new userDataObject();
                user.setUsername(username);//partition key

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withIndexName("username1")
                        .withHashKeyValues(user)
                        .withConsistentRead(false);

                PaginatedList<userDataObject> result = ((global_objects)getApplication()).getDynamoDBMapper().query(userDataObject.class, queryExpression);

                if(result.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextInputLayout user_name = findViewById(R.id.txt_username);
                            user_name.setErrorEnabled(false);

                        }
                    });                    try {
                        Intent k = new Intent(authentication_signup_identity.this, authentication_signup_bankinfo.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TextInputLayout user_name = findViewById(R.id.txt_username);
                            user_name.setError("Username already exists");

                        }
                    });
                }

            }
        }).start();
    }


    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        }
    }

