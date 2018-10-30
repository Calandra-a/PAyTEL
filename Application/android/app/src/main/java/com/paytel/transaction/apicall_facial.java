package com.paytel.transaction;

import android.app.Activity;
import android.util.Log;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.paytel.sign_up.authentication_signup_facial;
import com.paytel.util.api.id4pxy6j7c4b.AuthenticatefacialMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class apicall_facial{
    private static final String LOG_TAG = authentication_signup_facial.class.getSimpleName();
    String responseVal = null;

    private AuthenticatefacialMobileHubClient apiClient;

    public String callCloudLogic(String Image, String pose) {
        //apiClient = ((global_objects) getApplication()).getSignup_facial_api_client();
        apiClient =new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(AuthenticatefacialMobileHubClient.class);

        String userID =IdentityManager.getDefaultIdentityManager().getCachedUserID();
        //String userID = "us-east-1:2826223d-5f83-44c8-993c-b8dd105e202e";
        // Create components of api request
        final String method = "POST";

        final String path = "/authenticate-facial";

        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            json.put("pose", pose);
            json.put("image", Image);
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
