package com.paytel.sign_up;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.paytel.global_objects;
import com.paytel.util.api.iddd6h4gihxi.SignupfacialinitialiMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class authentication_apicall_facial extends Activity {
    private static final String LOG_TAG = authentication_signup_facial.class.getSimpleName();
    String responseVal = null;

    private SignupfacialinitialiMobileHubClient apiClient;

    public String callCloudLogic(String Image, String pose) {
        //apiClient = ((global_objects) getApplication()).getSignup_facial_api_client();
        apiClient =new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(SignupfacialinitialiMobileHubClient.class);

        String userID =IdentityManager.getDefaultIdentityManager().getCachedUserID();
        //String userID = "us-east-1:2826223d-5f83-44c8-993c-b8dd105e202e";
        // Create components of api request
        final String method = "POST";

        final String path = "/face";

        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("pose", pose);
            json.put("image", Image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String body = json.toString();

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

        // Only set body if it has content.

        //if (body.length() > 0) {
            System.out.println(body.length());
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(body.length()))
                    .withBody(body);
       // }

        final ApiRequest request = localRequest;

        // Make network call on background thread
       //new Thread(new Runnable() {
         //   @Override
           // public void run() {

                try {
                    Log.d(LOG_TAG,
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        final String responseData = IOUtils.toString(responseContentStream);
                        Log.d(LOG_TAG, "Response : " + responseData);
                    }

                    Log.d(LOG_TAG, response.getStatusCode() + " " + response.getStatusText());

                    if (response.getStatusCode() != 200) {
                        responseVal = "false";

                    }else {
                        responseVal = "true";
                    }


                } catch (final Exception exception) {
                    Log.e(LOG_TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
                return responseVal;
            }
        //}).start();




}
