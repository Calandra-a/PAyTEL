package com.paytel.util;

public class TransactionCard {

    private String mUsername;
    private String mAmount;
    private String mTransactionID;

    public TransactionCard(String mUsername, String mTransactionID, String mAmount ){
        this.mUsername = mUsername;
        this.mAmount = mAmount;
        this.mTransactionID = mTransactionID;
    }
    public String getmUsername() {
        return mUsername;
    }

    public String getmAmount() {
        return mAmount;
    }

    public String getmTransactionID() {
        return mTransactionID;
    }
}

