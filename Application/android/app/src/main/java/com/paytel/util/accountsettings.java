package com.paytel.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.paytel.R;

import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "csi-mobilehub-447478737-user-data")
public class accountsettings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accountsettings);

        Button btn_SAVE_userinfo = findViewById(R.id.btn_save_userinfo);
        Button btn_BACK_homepage = findViewById(R.id.btn_back_homepage);
    }

    private String _userId;
    private String _city;
    private String _country;
    private Map<String, String> _creditCard;
    private String _eMail;
    private String _firstName;
    private String _lastName;
    private String _phoneNumber;
    private Set<String> _rekognitionIds;
    private String _street;
    private String _username;
    private String _zipCode;



}
