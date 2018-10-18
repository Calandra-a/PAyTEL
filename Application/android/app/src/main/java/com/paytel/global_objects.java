package com.paytel;

import android.app.Application;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.paytel.util.TransactionDataObject;
import com.paytel.util.userDataObject;

public class global_objects extends Application {

    DynamoDBMapper dynamoDBMapper;
    userDataObject new_user;
    userDataObject current_user;

    TransactionDataObject new_transaction;
    TransactionDataObject current_transaction;

    public userDataObject getNew_user() {
        return new_user;
    }

    public void setNew_user(userDataObject new_user) {
        this.new_user = new_user;
    }

    public TransactionDataObject getNew_transaction() {
        return new_transaction;
    }

    public void setNew_transaction(TransactionDataObject new_transaction) { this.new_transaction = new_transaction;}

    public TransactionDataObject getCurrent_transaction() {
        return current_transaction;
    }

    public void setCurrent_transaction(TransactionDataObject current_transaction) { this.current_transaction = current_transaction;}

    public DynamoDBMapper getDynamoDBMapper() {return dynamoDBMapper;}

    public void setDynamoDBMapper(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public userDataObject getCurrent_user() { return current_user;}
    public void setCurrent_user(userDataObject current_user) { this.current_user = current_user;}


}
