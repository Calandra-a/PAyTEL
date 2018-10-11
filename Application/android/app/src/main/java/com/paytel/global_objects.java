package com.paytel;

import android.app.Application;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.paytel.util.userDataObject;

public class global_objects extends Application {

    DynamoDBMapper dynamoDBMapper;
    userDataObject new_user;
    userDataObject current_user;

    public userDataObject getNew_user() {
        return new_user;
    }

    public void setNew_user(userDataObject new_user) {
        this.new_user = new_user;
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    public void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public userDataObject getCurrent_user() { return current_user;}
    public void setCurrent_user(userDataObject current_user) { this.current_user = current_user;}


}
