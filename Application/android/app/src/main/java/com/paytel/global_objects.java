package com.paytel;

import android.app.Application;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.paytel.util.userData;

public class global_objects extends Application {

    DynamoDBMapper dynamoDBMapper;
    userData new_user;

    public userData getNew_user() {
        return new_user;
    }

    public void setNew_user(userData new_user) {
        this.new_user = new_user;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    public void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }



}
