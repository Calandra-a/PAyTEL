package com.paytel.sign_up;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.paytel.global_objects;
import com.paytel.util.api.iddd6h4gihxi.SignupfacialinitialiMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.amazonaws.mobileconnectors.s3.transferutility.*;

public class authentication_apicall_facial extends Activity {
    private static final String LOG_TAG = authentication_signup_facial.class.getSimpleName();
    Object[] responseArray = new Object[2];
    ApiResponse responseVal;
    String responseData;


    private SignupfacialinitialiMobileHubClient apiClient;

    public Object[] callCloudLogic(String pose) {

        String userID =IdentityManager.getDefaultIdentityManager().getCachedUserID();

        apiClient =new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(SignupfacialinitialiMobileHubClient.class);
        // Create components of api request
        final String method = "POST";

        final String path = "/face";

        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("pose", pose);
            //json.put("image", Image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String body = json.toString();
        System.out.println(body);

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

            System.out.println(body.length());
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(body.length()))
                    .withBody(body);

        final ApiRequest request = localRequest;
                try {
                    Log.d(LOG_TAG,
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        responseData = IOUtils.toString(responseContentStream);
                        Log.d(LOG_TAG, "Response : " + responseData);
                    }

                    responseVal = response;
                    responseArray[0] = responseVal;
                    responseArray[1] = responseData;
                    Log.d(LOG_TAG, response.getStatusCode() + " " + response.getStatusText());


                } catch (final Exception exception) {
                    Log.e(LOG_TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
                return responseArray;
            }
        //}).start();




}
