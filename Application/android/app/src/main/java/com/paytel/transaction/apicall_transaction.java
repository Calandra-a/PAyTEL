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
import com.paytel.util.api.idyonkpcbig0.UsertransactionMobileHubClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class apicall_transaction {
    private static final String LOG_TAG = apicall_transaction.class.getSimpleName();
    ApiResponse responseVal = null;

    private UsertransactionMobileHubClient apiClient;

    public ApiResponse callCloudLogic(String transID, String requesttype, String biometric) {
        apiClient =new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(UsertransactionMobileHubClient.class);

        String userID =IdentityManager.getDefaultIdentityManager().getCachedUserID();

        // Create components of api request
        final String method = "POST";

        final String path = "/complete-transaction";

        JSONObject json = new JSONObject();
        try {
            json.put("user_id", userID);
            json.put("transaction_id", transID);
            json.put("request", requesttype);
            json.put("biometric", biometric);
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
                responseVal = response;

            }else {
                responseVal = response;
            }


        } catch (final Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            exception.printStackTrace();
        }
        return responseVal;
    }
    //}).start();






}
