package com.paytel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.paytel.util.TransactionDataObject;
import com.paytel.util.accountsettings;
import com.paytel.transaction.create_new_transaction;
import com.paytel.transaction.start_buyer_transaction;
import com.paytel.util.add_funds;
import com.paytel.util.userDataObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class home extends AppCompatActivity{
    private TextView mTextMessage;
    private CardView mCardview;
    private static PinpointManager pinpointManager;

    userDataObject user;
    boolean nav_bool;
    ArrayList<String> transAmounts = new ArrayList<>();
    ArrayList<String> transIDs = new ArrayList<>();
    ArrayList<String> transStatus = new ArrayList<>();
    ArrayList<String> completedTransaction = new ArrayList<>();
    ArrayList<String> pendingTransaction = new ArrayList<>();
    Map<String, String> map = new HashMap<String, String>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    refreshTransactions();
                    nav_bool = false;
                    queryUser();
                    return true;
                case R.id.navigation_dashboard:
                    refreshTransactions();
                    nav_bool = true;
                    queryUser();
                    return true;
                }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView pendinglistView = (ListView) findViewById(R.id.pending_list);
        ListView completedlistView = (ListView) findViewById(R.id.completed_list);
        //set top toolbar
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        mTextMessage = (TextView) findViewById(R.id.message);
        mCardview = findViewById(R.id.cardView);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //System.out.println("user id: " + IdentityManager.getDefaultIdentityManager().getCachedUserID());
        //Log.d("HOME", IdentityManager.getDefaultIdentityManager().getCachedUserID());
        String userID = IdentityManager.getDefaultIdentityManager().getCachedUserID();

        queryUser();

        FloatingActionButton btn_fab = findViewById(R.id.fab_transaction);
        Button btn_funds = findViewById(R.id.btn_funds);

        pendinglistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                TextView label = arg1.findViewById(R.id.label);
                String viewString = label.getText().toString();
                String transactionNumber = viewString.substring(4,8);
                String amount = viewString.substring(viewString.lastIndexOf("$") + 1);;

                if(map.get(transactionNumber) != null){
                    Intent intent = new Intent(home.this, start_buyer_transaction.class);
                    intent.putExtra("name", map.get(transactionNumber));
                    startActivity(intent);
                }
            }
        });
        completedlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                TextView label = arg1.findViewById(R.id.label);
                String viewString = label.getText().toString();
                String transactionNumber = viewString.substring(4,8);
                String amount = viewString.substring(viewString.lastIndexOf("$") + 1);;

                if(map.get(transactionNumber) != null){
                    Intent intent = new Intent(home.this, start_buyer_transaction.class);
                    intent.putExtra("name", map.get(transactionNumber));
                    startActivity(intent);
                }
            }
        });

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(home.this, create_new_transaction.class);
                    startActivity(k);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_funds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    Intent k = new Intent(home.this, add_funds.class);
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
                    try {

                        Set<String> transactionSet = current_user.getTransactions();
                        ArrayList<String> dataSet = new ArrayList<>(transactionSet);
                        for (int i = 0; i < dataSet.size(); i++) {
                            TransactionDataObject transaction = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, dataSet.get(i));
                            transIDs.add(transaction.getTransactionId());
                            transAmounts.add(transaction.getAmount());
                            transStatus.add(transaction.getTransactionStatus());
                            map.put(transaction.getTransactionId().substring(0,4),transaction.getTransactionId());
                        }
                        initializingTranasactions();
                        if (result.isEmpty()) {
                            // There were no items matching your query.
                            Log.d("Query results: ", "none");
                        }
                    }
                    catch(NullPointerException e){
                        e.printStackTrace();
                        Log.d("Error", "No transactions being pulled?");
                    }
                }
            }
        }).start();
    }

    void initializingTranasactions(){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView pendinglistView = (ListView) findViewById(R.id.pending_list);
                    ListView completedlistView = (ListView) findViewById(R.id.completed_list);

                    Set<String> transactionSet = ((global_objects) getApplication()).getCurrent_user().getTransactions();
                    ArrayList<String> dataSet = new ArrayList<>(transactionSet);
                    for (int i = 0; i < dataSet.size(); i++) {
                        switch (transStatus.get(i)){
                            case "confirm":
                                completedTransaction.add("ID: " + transIDs.get(i).substring(0,4)+" " + "$" + transAmounts.get(i));
                                break;
                            case "pending":
                                pendingTransaction.add("ID: " + transIDs.get(i).substring(0,4)+" "  + "$" + transAmounts.get(i));
                                break;
                            case "flagged":
                                pendingTransaction.add("ID: " + transIDs.get(i).substring(0,4)+" " + "$" + transAmounts.get(i));
                                break;
                            case "cancel":
                                completedTransaction.add("ID: " + transIDs.get(i).substring(0,4)+" "  + "$" + transAmounts.get(i));
                                break;
                        }

                    }
                    ArrayAdapter adapterCompleted = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_listview, R.id.label, completedTransaction);
                    ArrayAdapter adapterPending = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_listview, R.id.label, pendingTransaction);

                    if(nav_bool == true)
                        completedlistView.setAdapter(adapterCompleted);
                    else{
                        pendinglistView.setAdapter(adapterPending);
                    }

                    TextView mCardview = (TextView) findViewById(R.id.info_text);
                    TextView mUsername = (TextView) findViewById(R.id.info_username);

                    Double wallet = ((global_objects) getApplication()).getCurrent_user().getWallet();
                    mCardview.setText("Wallet: $"+Double.toString(wallet));
                    mUsername.setText(((global_objects) getApplication()).getCurrent_user().getUsername());
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void refreshTransactions(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView pendinglistView = (ListView) findViewById(R.id.pending_list);
                ListView completedlistView = (ListView) findViewById(R.id.completed_list);
                pendinglistView.setAdapter(null);
                completedlistView.setAdapter(null);
                transAmounts.clear();
                transIDs.clear();
                pendingTransaction.clear();
                completedTransaction.clear();
            }
        });
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
