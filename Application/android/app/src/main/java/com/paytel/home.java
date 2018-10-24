package com.paytel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.paytel.sign_up.authentication_signup_identity;
import com.paytel.util.TransactionAdapter;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.accountsettings;
import com.paytel.transaction.initial_transaction;

import com.paytel.util.userDataObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class home extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView cardMessage;
    private static PinpointManager pinpointManager;

    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<TransactionDataObject> datah;
    private static ArrayList<String> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    userDataObject user;
    TransactionDataObject current_transaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    cardMessage.setText(R.string.title_dashboard);
                    user = ((global_objects) getApplication()).getCurrent_user();
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
        //currentTransactionMsg = (TextView) findViewById(R.id.txtTransaction);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        myOnClickListener = new MyOnClickListener(this);

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

                    //here
                    myOnClickListener = new MyOnClickListener(getApplicationContext());
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                    mRecyclerView.setHasFixedSize(true);

                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());



                    Set<String> transactionSet = current_user.getTransactions();
                    ArrayList<String> dataSet = new ArrayList<>(transactionSet);
                    ArrayList<TransactionDataObject> data = new ArrayList<>();

                    for (int i = 0; i < dataSet.size(); i++) {
                        TransactionDataObject transresult = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, dataSet.get(i));
                        Log.d("Result: ", transresult.getAmount());
                       /* data.add(
                                transresult.getTransactionId(),
                                transresult.getAmount(),
                                transresult.getBuyerId(),
                                transresult.getNote(),
                                transresult.getSellerId(),
                                transresult.getTransactionStatus(),
                                transresult.getTime(),
                                transresult.getAuthenticationType());
                                */
                    }
                    /*
                    for(String transID : transactionList) {
                        transaction.setTransactionId(transID);//partition key
                        TransactionDataObject transresult = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, transID);
                        Log.d("Query results: ", transID);

                        try{
                            Log.d("Query results: ", transresult.getAmount());
                        }
                        catch(Exception e){}
                    }*/
                    // Add your code here to deal with the data result
                    mAdapter = new TransactionAdapter(data);
                    mRecyclerView.setAdapter(mAdapter);

                    if (result.isEmpty()) {
                        // There were no items matching your query.
                        Log.d("Query results: ", "none");
                    }

                }
            }
        }).start();
    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
           // removeItem(v);
        }

        /*private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }*/
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
