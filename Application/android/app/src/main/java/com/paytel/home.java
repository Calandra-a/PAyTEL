package com.paytel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import com.google.gson.JsonParser;
import com.paytel.sign_up.authentication_signup_address;
import com.paytel.sign_up.authentication_signup_facial;
import com.paytel.sign_up.authentication_signup_identity;
import com.paytel.util.accountsettings;
import com.paytel.transaction.initial_transaction;

import com.paytel.util.userDataObject;

public class home extends AppCompatActivity {
    private TextView mTextMessage;

    private static PinpointManager pinpointManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set top toolbar
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        System.out.println("user id: " + IdentityManager.getDefaultIdentityManager().getCachedUserID());
        Log.d("HOME", IdentityManager.getDefaultIdentityManager().getCachedUserID());

        String userID = IdentityManager.getDefaultIdentityManager().getCachedUserID();
        queryUser();

        FloatingActionButton btn_fab =findViewById(R.id.fab_transaction);
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                    try {
                        Intent k = new Intent(home.this, initial_transaction.class);
                        startActivity(k);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topbar_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_settingspage) {
            try {
                Intent k = new Intent(home.this, accountsettings.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void queryUser(){
        new Thread(new Runnable() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void run() {
                userDataObject user = new userDataObject();
                user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());//partition key

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(user)
                        .withConsistentRead(false);

                PaginatedList<userDataObject> result = ((global_objects)getApplication()).getDynamoDBMapper().query(userDataObject.class, queryExpression);

                Gson gson = new Gson();
                JsonParser parser = new JsonParser();

                StringBuilder stringBuilder = new StringBuilder();
                // Loop through query results
                for (int i = 0; i < result.size(); i++) {
                    String jsonFormOfItem = gson.toJson(result.get(i));
                    stringBuilder.append(jsonFormOfItem + "\n\n");
                }

                // Add your code here to deal with the data result
                Log.d("Query results: ", stringBuilder.toString());
                if (result.isEmpty()) {
                    // There were no items matching your query.
                    Log.d("Query results: ", "none");
                    //go to sign up activity
                    try {
                        Intent k = new Intent(home.this, authentication_signup_identity.class);
                        startActivity(k);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //add current device token to db
                    userDataObject uu = new userDataObject();
                    uu.setDevicePushId(getPinpointManager(getApplicationContext()).getNotificationClient().getDeviceToken());
                    uu.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());
                    ((global_objects)getApplication()).getDynamoDBMapper().save(uu, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES));

                    userDataObject current_user = ((global_objects)getApplication()).getDynamoDBMapper().load(userDataObject.class, IdentityManager.getDefaultIdentityManager().getCachedUserID());
                        ((global_objects) getApplication()).setCurrent_user(current_user);

                }
            }
        }).start();
    }

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance().getCredentialsProvider(),
                    AWSMobileClient.getInstance().getConfiguration());

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            final String token = task.getResult().getToken();
                            Log.d("HOME", "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        Log.d("AXELWAS",pinpointManager.getPinpointContext().getUniqueId());

        return pinpointManager;
    }


}
