package com.paytel.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.paytel.R;
import com.paytel.global_objects;
import com.paytel.home;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.api.idyonkpcbig0.UsertransactionMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class initial_transaction extends AppCompatActivity {

    private UsertransactionMobileHubClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.transaction_seller);

        //credit card
        Button btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to next frame
                add_transactioninfo();

                try {
                    Intent k = new Intent(initial_transaction.this, home.class);
                    startActivity(k);

                } catch (Exception e) {
                    e.printStackTrace();
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

    void add_transactioninfo(){
        TextInputLayout buyerID = findViewById(R.id.txt_buyerID);
        TextInputLayout amount = findViewById(R.id.txt_amount);
        TextInputLayout note = findViewById(R.id.txt_note);

        //if(buyerID != null)new_transaction.setSellerId();
        //if(amount != null)new_transaction.setAmount();
        //if(note != null)new_transaction.setNote();

        //do this call if everything checks out
        doApiCall(buyerID.getEditText().getText().toString().trim(), amount.getEditText().getText().toString().trim(), note.getEditText().getText().toString().trim());
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
}