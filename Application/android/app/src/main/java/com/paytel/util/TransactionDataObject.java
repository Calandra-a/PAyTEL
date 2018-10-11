package com.paytel.util;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "csi-mobilehub-447478737-transactions")

public class TransactionDataObject {
    private String _transactionId;
    private String _amount;
    private String _authenticationType;
    private String _buyerId;
    private String _note;
    private String _sellerId;
    private String _status;
    private String _time;

    @DynamoDBHashKey(attributeName = "transaction_id")
    @DynamoDBAttribute(attributeName = "transaction_id")
    public String getTransactionId() {
        return _transactionId;
    }

    public void setTransactionId(final String _transactionId) {
        this._transactionId = _transactionId;
    }
    @DynamoDBAttribute(attributeName = "amount")
    public String getAmount() {
        return _amount;
    }

    public void setAmount(final String _amount) {
        this._amount = _amount;
    }
    @DynamoDBAttribute(attributeName = "authentication_type")
    public String getAuthenticationType() {
        return _authenticationType;
    }

    public void setAuthenticationType(final String _authenticationType) {
        this._authenticationType = _authenticationType;
    }
    @DynamoDBAttribute(attributeName = "buyer_id")
    public String getBuyerId() {
        return _buyerId;
    }

    public void setBuyerId(final String _buyerId) {
        this._buyerId = _buyerId;
    }
    @DynamoDBAttribute(attributeName = "note")
    public String getNote() {
        return _note;
    }

    public void setNote(final String _note) {
        this._note = _note;
    }
    @DynamoDBAttribute(attributeName = "seller_id")
    public String getSellerId() {
        return _sellerId;
    }

    public void setSellerId(final String _sellerId) {
        this._sellerId = _sellerId;
    }
    @DynamoDBAttribute(attributeName = "status")
    public String getStatus() {
        return _status;
    }

    public void setStatus(final String _status) {
        this._status = _status;
    }
    @DynamoDBAttribute(attributeName = "time")
    public String getTime() {
        return _time;
    }

    public void setTime(final String _time) {
        this._time = _time;
    }

}
