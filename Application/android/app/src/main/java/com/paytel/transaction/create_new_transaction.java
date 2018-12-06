package com.paytel.transaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.util.IOUtils;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.api.idyonkpcbig0.UsertransactionMobileHubClient;
import com.paytel.util.userDataObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class create_new_transaction extends AppCompatActivity {

    private UsertransactionMobileHubClient apiClient;
    private static int transactionCount = 0;
    private static boolean lock = false;
    private Toast toast = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //transactionCount = 0;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_seller);

        //credit card
        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //move to next frame

                if (transactionCount == 0){
                    setTimer();
                }
                transactionCount += 1;
                 if (lock == false && transactionCount == 6){

                    Context context = getApplicationContext();
                    int dLong = Toast.LENGTH_LONG;
                    CharSequence fail = "You're creating transactions too quickly - you can make another one in 1 minute";
                     if (toast != null) {
                         toast.cancel();
                     }
                    Toast toast = Toast.makeText(context, fail, dLong);
                    toast.show();
                    lock = true;
                    lockout();
                }
                if (lock == true){
                     Context context = getApplicationContext();
                     int dShort = Toast.LENGTH_SHORT;
                     CharSequence fail = "Locked - Please wait";
                    if (toast != null) {
                        toast.cancel();
                    }
                     Toast toast = Toast.makeText(context, fail, dShort);
                     toast.show();
                 }
                if(lock == false) {
                     add_transactioninfo();
                    //transactionCompleted();

                    }
                 }

        });
        //load api gateway
        apiClient =new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(UsertransactionMobileHubClient.class);

        //toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.transaction_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Make a payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
        });
    }

    public void transactionCompleted() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Transaction created");
        alert.setMessage("");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Do something here where "ok" clicked and then perform intent from activity context
                try {
                    Intent k = new Intent(create_new_transaction.this, home.class);
                    startActivity(k);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        alert.show();


    }
        void setTimer(){
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //debugging toast
                // Context context = getApplicationContext();
                //int dShort = Toast.LENGTH_SHORT;
                //CharSequence value = String.valueOf("count  = "+transactionCount);
                // Toast toast = Toast.makeText(context, value, dShort);
                //toast.show();
            }

            public void onFinish() {
                transactionCount = 0;

            }

        }.start();

    }


    void lockout(){
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                //debugging toast
                // Context context = getApplicationContext();
                //int dShort = Toast.LENGTH_SHORT;
                //CharSequence value = String.valueOf("count  = "+transactionCount);
                // Toast toast = Toast.makeText(context, value, dShort);
                //toast.show();
            }

            public void onFinish() {
                lock = false;
                transactionCount = 0;

                //debugging toast
                Context context = getApplicationContext();
                int dShort = Toast.LENGTH_SHORT;
                CharSequence value = "Transactions unlocked";
                if (toast != null) {
                    toast.cancel();
                }
                Toast toast = Toast.makeText(context, value, dShort);
                toast.show();
            }
        }.start();
    }



    void add_transactioninfo(){
        Context context = getApplicationContext();
        int dShort = Toast.LENGTH_SHORT;

        TextInputLayout buyerID = findViewById(R.id.txt_buyerID);
        TextInputLayout amount = findViewById(R.id.txt_amount);
        TextInputLayout note = findViewById(R.id.txt_note);

        boolean fail = false;
        if(buyerID.getEditText().getText().toString().toLowerCase().isEmpty()) {
            buyerID.setError("No field can be left blank");
            fail = true;
        }
        else{
            buyerID.setErrorEnabled(false);
        }
        if( amount.getEditText().getText().toString().toLowerCase().isEmpty()){
            amount.setError("No field can be left blank");
            fail = true;

        }
        else if( Double.parseDouble(amount.getEditText().getText().toString().trim()) < 1){

            amount.setError("Amount must $1 or more");
            fail = true;
        }
        else{
            amount.setErrorEnabled(false);
        }
        if(note.getEditText().getText().toString().toLowerCase().isEmpty()){
            note.setError("No field can be left blank");
            fail = true;
        }
        else{
            note.setErrorEnabled(false);
        }

        if(fail == false) {
            //CharSequence fail = buyerID.getEditText().getText().toString().toLowerCase();
            //Toast toast = Toast.makeText(context, fail, dShort);
            //toast.show();
            check_username(buyerID.getEditText().getText().toString().toLowerCase());
        }


    }

    void doApiCall(String buyerID, String amount, String note){
        // Create components of api request
        final String method = "POST";

        final String path = "/create-transaction";

        final Map parameters = new HashMap<>();
        parameters.put("lang", "en_US");

        final Map headers = new HashMap<>();

        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(parameters);


        JSONObject json = new JSONObject();
        try {
            json.put("seller_user_id", ((global_objects) getApplication()).getCurrent_user().getUserId());
            json.put("seller_username", ((global_objects) getApplication()).getCurrent_user().getUsername());
            json.put("buyer_username", buyerID);
            json.put("amount", amount);
            json.put("note", note);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String body = json.toString();
        // Only set body if it has content.

        if (body.length() > 0) {
            System.out.println(body.length());
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(body.length()))
                    .withBody(body);
        }

        final ApiRequest request = localRequest;

        // Make network call on background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("API CALL",
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    final ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        final String responseData = IOUtils.toString(responseContentStream);
                        Log.d("API CALL", "Response : " + responseData);
                    }

                    Log.d("API CALL", response.getStatusCode() + " " + response.getStatusText());

                } catch (final Exception exception) {
                    Log.e("API CALL", exception.getMessage(), exception);
                    exception.printStackTrace();
                }
            }
        }).start();
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
                            TextInputLayout buyerID = findViewById(R.id.txt_buyerID);
                            buyerID.setError("No field can be left blank");
                        }
                    });

                }
                else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                    TextInputLayout buyerID = findViewById(R.id.txt_buyerID);
                    TextInputLayout amount = findViewById(R.id.txt_amount);
                    TextInputLayout note = findViewById(R.id.txt_note);
                    buyerID.setErrorEnabled(false);
                    //do this call if everything checks out
                    doApiCall(buyerID.getEditText().getText().toString().trim(), amount.getEditText().getText().toString().trim(), note.getEditText().getText().toString().trim());

                        }
                    });
                    runOnUiThread(new Runnable() {
                        public void run() {
                            transactionCompleted();
                        }
                    });

                }

            }
        }).start();
    }

}