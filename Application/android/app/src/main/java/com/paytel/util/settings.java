package com.paytel.util;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "csi-mobilehub-447478737-user-data")
public class settings {
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
