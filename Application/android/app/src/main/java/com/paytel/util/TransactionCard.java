package com.paytel.util;

public class TransactionCard {

    private String mUsername;
    private String mBuyer;
    private String mSeller;
    private String mAmount;
    private String mTransactionID;
    private String mStatus;

    public TransactionCard(String mUsername, String mBuyer, String mSeller, String mTransactionID, String mAmount, String mStatus){
        this.mUsername = mUsername;
        this.mBuyer = mBuyer;
        this.mSeller = mSeller;
        this.mAmount = mAmount;
        this.mTransactionID = mTransactionID;
        this.mStatus = mStatus;
    }
    public String getmUsername() {
        return mUsername;
    }

    public String getmBuyer() {
        return mBuyer;
    }

    public String getmSeller() {
        return mSeller;
    }

    public String getmAmount() {
        return mAmount;
    }

    public String getmTransactionID() {
        return mTransactionID;
    }

    public String getmStatus() {
        return mStatus;
    }
}

