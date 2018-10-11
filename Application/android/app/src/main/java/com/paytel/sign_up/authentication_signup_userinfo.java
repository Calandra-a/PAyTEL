package com.paytel.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.util.userDataObject;

public class authentication_signup_userinfo  extends AppCompatActivity {
    userDataObject new_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.authentication_activity_signup_userinfo);

        ((global_objects) getApplication()).setNew_user(new userDataObject());
        new_user = ((global_objects) getApplication()).getNew_user();

        //user data
        Button btn_NEXT_userdata = findViewById(R.id.btn_next_userinfo);

        btn_NEXT_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_userinfo();
                //move to next frame
                try {
                    Intent k = new Intent(authentication_signup_userinfo.this, authentication_signup_bankinfo.class);
                    startActivity(k);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void add_userinfo(){
        TextView f_name = findViewById(R.id.txt_first_name);
        TextView l_name = findViewById(R.id.txt_last_name);
        TextView user_name = findViewById(R.id.txt_username);
        TextView street = findViewById(R.id.txt_street);
        TextView zip = findViewById(R.id.txt_zipcode);
        TextView phone_number = findViewById(R.id.txt_phone_number);
        TextView city = findViewById(R.id.txt_city);

        new_user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());
        new_user.setUsername(user_name.getText().toString());
        new_user.setFirstName(f_name.getText().toString());
        new_user.setLastName(l_name.getText().toString());
        new_user.setStreet(street.getText().toString());
        new_user.setCity(city.getText().toString());
        new_user.setZipCode(zip.getText().toString());
        new_user.setPhoneNumber(phone_number.getText().toString());
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
