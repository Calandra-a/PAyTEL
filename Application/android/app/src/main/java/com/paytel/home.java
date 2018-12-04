package com.paytel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
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
import com.paytel.util.TransactionAdapter;
import com.paytel.util.TransactionCard;
import com.paytel.util.TransactionDataObject;
import com.paytel.settings.settings_main;
import com.paytel.transaction.create_new_transaction;
import com.paytel.transaction.start_buyer_transaction;
import com.paytel.util.add_funds;
import com.paytel.util.userDataObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class home extends AppCompatActivity{
    private TextView mTextMessage;
    private CardView mCardview;

    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet = new ConstraintSet();
//axel

    List<TransactionDataObject> my_pending_transactions = new ArrayList<TransactionDataObject>();
    List<TransactionDataObject> my_completed_transactions = new ArrayList<TransactionDataObject>();
    boolean first_run = true;
    //marsol
    boolean nav_bool;
    ArrayList<String> transAmounts = new ArrayList<>();
    ArrayList<String> transIDs = new ArrayList<>();
    private ArrayList<String> transSeller = new ArrayList<>();
    private ArrayList<String> transBuyer = new ArrayList<>();
    ArrayList<String> transStatus = new ArrayList<>();
    ArrayList<String> transTime = new ArrayList<>();
    ArrayList<TransactionCard> completedTransaction = new ArrayList<>();
    ArrayList<TransactionCard> pendingTransaction = new ArrayList<>();
    private TransactionAdapter tPendingAdapter, tCompleteAdapter;
    private long mLastClickTime = 0;
    private boolean background = true;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                        return true;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    background = false;
                    nav_bool = false;
                    showTransaction();
                    return true;
                case R.id.navigation_dashboard:
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                        return true;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    background = false;
                    nav_bool = true;
                    showTransaction();

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
        mConstraintLayout = findViewById(R.id.container);
        //set top toolbar
        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        background = true;
        mTextMessage = (TextView) findViewById(R.id.message);
        mCardview = findViewById(R.id.cardView);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //queryInBackground();

        FloatingActionButton btn_fab = findViewById(R.id.fab_transaction);
        Button btn_funds = findViewById(R.id.btn_funds);

        pendinglistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return;
                }
                try{
                    mLastClickTime = SystemClock.elapsedRealtime();
                    TextView invis = arg1.findViewById(R.id.txt_invisID);
                    String viewString = invis.getText().toString();
                    background = false;
                    Intent intent = new Intent(home.this, start_buyer_transaction.class);
                    intent.putExtra("name", viewString);
                    startActivity(intent);
                }
                catch(Exception e){
                }
            }
        });
        completedlistView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500){
                    return;
                }
                try{
                    mLastClickTime = SystemClock.elapsedRealtime();
                    TextView invis = arg1.findViewById(R.id.txt_invisID);
                    String viewString = invis.getText().toString();
                    background = false;
                    Intent intent = new Intent(home.this, start_buyer_transaction.class);
                    intent.putExtra("name", viewString);
                    startActivity(intent);
                }
                catch(Exception e){
                }
            }
        });

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                try {
                    background = false;
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
    public void onResume(){
        super.onResume();
        queryInBackground();
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
                //Intent k = new Intent(home.this, accountsettings.class);
                Intent k = new Intent(home.this, settings_main.class);
                startActivity(k);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void queryInBackground(){
        new Thread(new Runnable() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void run() {
                while(background){
                    userDataObject user = new userDataObject();
                    user.setUserId(IdentityManager.getDefaultIdentityManager().getCachedUserID());//partition key

                    DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                            .withHashKeyValues(user)
                            .withConsistentRead(false);

                    PaginatedList<userDataObject> result = ((global_objects)getApplication()).getDynamoDBMapper().query(userDataObject.class, queryExpression);

                    if (result.isEmpty()) {
                        // There were no items matching your query.
                        Log.d("Query results: ", "none");
                        //go to sign up activity
                        try {
                            background = false;
                            Intent k = new Intent(home.this, authentication_signup_identity.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        userDataObject current_user = ((global_objects)getApplication()).getDynamoDBMapper().load(userDataObject.class, IdentityManager.getDefaultIdentityManager().getCachedUserID());
                        ((global_objects) getApplication()).setCurrent_user(current_user);

                        try {

                            /*if(!first_run){
                                Set<String> old_transaction_set = ((global_objects) getApplication()).getUserTransactions();
                                ((global_objects) getApplication()).setUserTransactions(current_user.getTransactions());
                                copy.removeAll(old_transaction_set);
                            }else{
                                ((global_objects) getApplication()).setUserTransactions(current_user.getTransactions());
                            }
                            first_run = false;*/
                            //set only contains changed items

                            ArrayList<String> dataSet = new ArrayList<>(current_user.getTransactions());
                            System.out.println("hello axel " + dataSet.size());
                            my_pending_transactions.clear();
                            my_completed_transactions.clear();
                            for (int i = 0; i < dataSet.size(); i++) {
                                TransactionDataObject transaction = ((global_objects) getApplication()).getDynamoDBMapper().load(TransactionDataObject.class, dataSet.get(i));
                                if(transaction.getTransactionStatus().toLowerCase().equals("pending")){
                                    my_pending_transactions.add(transaction);
                                }else{
                                    my_completed_transactions.add(transaction);
                                }
                            }

                            if(dataSet.size() > 0){
                                System.out.println("cool trans"+ dataSet.size());
                                initializingTranasactions();

                            }

                            if (result.isEmpty()) {
                                // There were no items matching your query.
                                initialSignup();
                                Log.d("Query results: ", "none");
                            }
                        }
                        catch(Exception e){
                            Log.d("Error", "No transactions being pulled?" + e);
                            initialSignup();
                        }
                    }
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                    try{
                        ListView pendinglistView = (ListView) findViewById(R.id.pending_list);
                        ListView completedlistView = (ListView) findViewById(R.id.completed_list);

                        String currentUserName = ((global_objects) getApplication()).getCurrent_user().getUsername();

                        pendingTransaction.clear();
                        completedTransaction.clear();
                        for (TransactionDataObject item : my_pending_transactions) {
                            try {
                                pendingTransaction.add(new TransactionCard(currentUserName, item.getBuyerUsername(), item.getSellerUsername(), item.getTransactionId(), '$' + item.getAmount(), item.getTransactionStatus(), item.getTimeCreated()));
                            }catch(Exception e){
                                }
                            }
                        for (TransactionDataObject item : my_completed_transactions) {
                            try {
                                completedTransaction.add(new TransactionCard(currentUserName, item.getBuyerUsername(), item.getSellerUsername(), item.getTransactionId(), '$' + item.getAmount(), item.getTransactionStatus(), item.getTimeCreated()));
                            }catch(Exception e){
                            }
                        }
                        try {
                            tCompleteAdapter = new TransactionAdapter(getApplicationContext(), completedTransaction);
                            tCompleteAdapter.notifyDataSetChanged();
                            tPendingAdapter = new TransactionAdapter(getApplicationContext(), pendingTransaction);
                            tPendingAdapter.notifyDataSetChanged();
                        }
                        catch(Exception e){
                        }
                        if(nav_bool == true) {
                            try{
                                pendinglistView.setVisibility(View.INVISIBLE);
                                completedlistView.setVisibility(View.VISIBLE);
                                completedlistView.setAdapter(tCompleteAdapter);
                                mConstraintSet.clone(mConstraintLayout);
                                mConstraintSet.connect(R.id.completed_list, ConstraintSet.TOP,
                                        R.id.cardView, ConstraintSet.BOTTOM);
                                mConstraintSet.connect(R.id.completed_list, ConstraintSet.BOTTOM,
                                        R.id.navigation, ConstraintSet.TOP);
                                mConstraintSet.applyTo(mConstraintLayout);
                            }
                            catch(Exception e){
                            }
                        }
                        else{
                            try{
                                completedlistView.setVisibility(View.INVISIBLE);
                                pendinglistView.setVisibility(View.VISIBLE);
                                pendinglistView.setAdapter(tPendingAdapter);
                                mConstraintSet.clone(mConstraintLayout);
                                mConstraintSet.connect(R.id.pending_list, ConstraintSet.TOP,
                                        R.id.cardView, ConstraintSet.BOTTOM);
                                mConstraintSet.connect(R.id.pending_list, ConstraintSet.BOTTOM,
                                        R.id.navigation, ConstraintSet.TOP);
                                mConstraintSet.applyTo(mConstraintLayout);
                            }
                            catch(Exception e){
                            }
                        }
                        try {
                            tPendingAdapter.notifyDataSetChanged();
                            tCompleteAdapter.notifyDataSetChanged();
                            TextView mCardview = (TextView) findViewById(R.id.info_text);
                            TextView mUsername = (TextView) findViewById(R.id.info_username);

                            Double wallet = ((global_objects) getApplication()).getCurrent_user().getWallet();
                            mCardview.setText("Wallet: $" + Double.toString(wallet));
                            mUsername.setText(currentUserName);
                        }
                        catch(Exception e){
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    } }

            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    void showTransaction(){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        ListView pendinglistView = (ListView) findViewById(R.id.pending_list);
                        ListView completedlistView = (ListView) findViewById(R.id.completed_list);

                        if(nav_bool == true) {
                            try {
                                pendinglistView.setVisibility(View.INVISIBLE);
                                completedlistView.setVisibility(View.VISIBLE);
                                completedlistView.setAdapter(tCompleteAdapter);
                                mConstraintSet.clone(mConstraintLayout);
                                mConstraintSet.connect(R.id.completed_list, ConstraintSet.TOP,
                                        R.id.cardView, ConstraintSet.BOTTOM);
                                mConstraintSet.connect(R.id.completed_list, ConstraintSet.BOTTOM,
                                        R.id.navigation, ConstraintSet.TOP);
                                mConstraintSet.applyTo(mConstraintLayout);
                                tPendingAdapter.notifyDataSetChanged();
                                tCompleteAdapter.notifyDataSetChanged();
                            }
                            catch(Exception e){
                            }
                        }
                        else {
                            try{
                                completedlistView.setVisibility(View.INVISIBLE);
                                pendinglistView.setVisibility(View.VISIBLE);
                                pendinglistView.setAdapter(tPendingAdapter);
                                mConstraintSet.clone(mConstraintLayout);
                                mConstraintSet.connect(R.id.pending_list, ConstraintSet.TOP,
                                        R.id.cardView, ConstraintSet.BOTTOM);
                                mConstraintSet.connect(R.id.pending_list, ConstraintSet.BOTTOM,
                                        R.id.navigation, ConstraintSet.TOP);
                                mConstraintSet.applyTo(mConstraintLayout);
                                tPendingAdapter.notifyDataSetChanged();
                                tCompleteAdapter.notifyDataSetChanged();
                            }
                            catch(Exception e){
                            }
                        }
                        background = true;
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void initialSignup(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    TextView mCardview = (TextView) findViewById(R.id.info_text);
                    TextView mUsername = (TextView) findViewById(R.id.info_username);

                    Double wallet = ((global_objects) getApplication()).getCurrent_user().getWallet();
                    mCardview.setText("Wallet: $"+Double.toString(wallet));
                    mUsername.setText(((global_objects) getApplication()).getCurrent_user().getUsername());
                }
                catch(Exception E){
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}
