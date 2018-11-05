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

@DynamoDBTable(tableName = "paytel-mobilehub-2098009603-transactions")

public class TransactionDataObject {
    private String _transactionId;
    private String _amount;
    private String _biometricUsed;
    private String _buyerId;
    private String _buyerUsername;
    private String _note;
    private String _sellerId;
    private String _sellerUsername;
    private String _time;
    private String _timeLastEdit;
    private String _transactionStatus;

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
    @DynamoDBAttribute(attributeName = "biometric_used")
    public String getBiometricUsed() {
        return _biometricUsed;
    }

    public void setBiometricUsed(final String _biometricUsed) {
        this._biometricUsed = _biometricUsed;
    }
    @DynamoDBAttribute(attributeName = "buyer_id")
    public String getBuyerId() {
        return _buyerId;
    }

    public void setBuyerId(final String _buyerId) {
        this._buyerId = _buyerId;
    }
    @DynamoDBAttribute(attributeName = "buyer_username")
    public String getBuyerUsername() {
        return _buyerUsername;
    }

    public void setBuyerUsername(final String _buyerUsername) {
        this._buyerUsername = _buyerUsername;
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
    @DynamoDBAttribute(attributeName = "seller_username")
    public String getSellerUsername() {
        return _sellerUsername;
    }

    public void setSellerUsername(final String _sellerUsername) {
        this._sellerUsername = _sellerUsername;
    }
    @DynamoDBAttribute(attributeName = "time")
    public String getTime() {
        return _time;
    }

    public void setTime(final String _time) {
        this._time = _time;
    }
    @DynamoDBAttribute(attributeName = "time_last_edit")
    public String getTimeLastEdit() {
        return _timeLastEdit;
    }

    public void setTimeLastEdit(final String _timeLastEdit) {
        this._timeLastEdit = _timeLastEdit;
    }
    @DynamoDBAttribute(attributeName = "transaction_status")
    public String getTransactionStatus() {
        return _transactionStatus;
    }

    public void setTransactionStatus(final String _transactionStatus) {
        this._transactionStatus = _transactionStatus;
    }

}
