package com.paytel.sign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                //bool controls if the button transitions, controlled by user data entry
                boolean next = add_userinfo();
                //move to next frame
                if (next == true){
                    try {
                        Intent k = new Intent(authentication_signup_userinfo.this, authentication_signup_bankinfo.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    boolean add_userinfo(){
        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;
        int dLong = Toast.LENGTH_SHORT;

        TextView f_name = findViewById(R.id.txt_first_name);
        TextView l_name = findViewById(R.id.txt_last_name);
        TextView user_name = findViewById(R.id.txt_username);
        TextView street = findViewById(R.id.txt_street);
        TextView zip = findViewById(R.id.txt_zipcode);
        TextView phone_number = findViewById(R.id.txt_phone_number);
        TextView city = findViewById(R.id.txt_city);

        //Using Toasts for incorrect data feedback --> replace with better dialog(or better solution)
        if(f_name.getText().length() == 0 || l_name.getText().length() == 0 || user_name.getText().length() == 0 || street.getText().length() == 0 ||
                city.getText().length() == 0 || zip.getText().length() == 0 || phone_number.getText().length() == 0){
            CharSequence fail = "No field can be left blank";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(f_name.getText().toString().length() >= 20) {
            CharSequence fail = "First name cant be over 20 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(l_name.getText().toString().length() >= 20) {
            CharSequence fail = "Last name cant be over 20 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(user_name.getText().toString().length() >= 15){
            CharSequence fail = "Username cant be over 15 characters";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        //check if phone number is 12 digits (including 2 dashes)
        else if (phone_number.getText().toString().length() != 12){
            CharSequence fail = "Phone number must be 12 characters long in xxx-xxx-xxxx format";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else if(zip.getText().toString().length() != 5) {
            CharSequence fail = "Zip code is not 5 digits long";
            Toast toast = Toast.makeText(context, fail, dLong);
            toast.show();
            return false;
        }
        else {
            CharSequence succ = "success";
            Toast success = Toast.makeText(context, succ, dShort);
            success.show();
            new_user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());
            new_user.setUsername(user_name.getText().toString());
            new_user.setFirstName(f_name.getText().toString());
            new_user.setLastName(l_name.getText().toString());
            new_user.setStreet(street.getText().toString());
            new_user.setCity(city.getText().toString());
            new_user.setZipCode(zip.getText().toString());
            new_user.setPhoneNumber(phone_number.getText().toString());
            return true;
        }
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



