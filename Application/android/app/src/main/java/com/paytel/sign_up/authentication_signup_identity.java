package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;

//Alex Dapoz
//I created this screen to make the signup process cleaner
//TODO: logic


public class authentication_signup_identity extends AppCompatActivity {
    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authentication_signup_identity);

        ((global_objects) getApplication()).setNew_user(new userDataObject());
        new_user = ((global_objects) getApplication()).getNew_user();

        //user data
        Button btn_NEXT_userdata = findViewById(R.id.btn_next_identity);

        btn_NEXT_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_userinfo();
                //move to next frame
                try {
                    Intent k = new Intent(authentication_signup_identity.this, authentication_signup_bankinfo.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void add_userinfo(){
        TextInputLayout f_name = findViewById(R.id.txt_first_name);
        TextInputLayout l_name = findViewById(R.id.txt_last_name);
        TextInputLayout user_name = findViewById(R.id.txt_username);
        TextInputLayout phone_number = findViewById(R.id.txt_phone_number);

        new_user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());
        new_user.setUsername(user_name.getEditText().getText().toString().trim());
        new_user.setFirstName(f_name.getEditText().getText().toString().trim());
        new_user.setLastName(l_name.getEditText().getText().toString().trim());
        new_user.setPhoneNumber(phone_number.getEditText().getText().toString().trim());
    }

    void check_username(final String username){
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
                        .withHashKeyValues(user)
                        .withConsistentRead(false);

                PaginatedList<userDataObject> result = ((global_objects)getApplication()).getDynamoDBMapper().query(userDataObject.class, queryExpression);


                if(result.isEmpty()) {

                }else{

                }
            }
        }).start();
    }
}
