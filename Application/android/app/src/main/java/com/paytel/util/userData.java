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

@DynamoDBTable(tableName = "csi-mobilehub-447478737-user-data")

    public class userData {
        private String _userId;
        private String _city;
        private String _country;
        private Map<String, String> _creditCard;
        private String _devicePushId;
        private String _eMail;
        private String _firstName;
        private String _lastName;
        private String _phoneNumber;
        private Set<String> _rekognitionIds;
        private String _street;
        private String _username;
        private String _zipCode;

        @DynamoDBHashKey(attributeName = "userId")
        @DynamoDBAttribute(attributeName = "userId")
        public String getUserId() {
            return _userId;
        }

        public void setUserId(final String _userId) {
            this._userId = _userId;
        }
        @DynamoDBAttribute(attributeName = "city")
        public String getCity() {
            return _city;
        }

        public void setCity(final String _city) {
            this._city = _city;
        }
        @DynamoDBAttribute(attributeName = "country")
        public String getCountry() {
            return _country;
        }

        public void setCountry(final String _country) {
            this._country = _country;
        }
        @DynamoDBAttribute(attributeName = "credit_card")
        public Map<String, String> getCreditCard() {
            return _creditCard;
        }

        public void setCreditCard(final Map<String, String> _creditCard) {
            this._creditCard = _creditCard;
        }
        @DynamoDBAttribute(attributeName = "device_push_id")
        public String getDevicePushId() {
            return _devicePushId;
        }

        public void setDevicePushId(final String _devicePushId) {
            this._devicePushId = _devicePushId;
        }
        @DynamoDBAttribute(attributeName = "e_mail")
        public String getEMail() {
            return _eMail;
        }

        public void setEMail(final String _eMail) {
            this._eMail = _eMail;
        }
        @DynamoDBAttribute(attributeName = "first_name")
        public String getFirstName() {
            return _firstName;
        }

        public void setFirstName(final String _firstName) {
            this._firstName = _firstName;
        }
        @DynamoDBAttribute(attributeName = "last_name")
        public String getLastName() {
            return _lastName;
        }

        public void setLastName(final String _lastName) {
            this._lastName = _lastName;
        }
        @DynamoDBIndexRangeKey(attributeName = "phone_number", globalSecondaryIndexName = "username1")
        public String getPhoneNumber() {
            return _phoneNumber;
        }

        public void setPhoneNumber(final String _phoneNumber) {
            this._phoneNumber = _phoneNumber;
        }
        @DynamoDBAttribute(attributeName = "rekognition_ids")
        public Set<String> getRekognitionIds() {
            return _rekognitionIds;
        }

        public void setRekognitionIds(final Set<String> _rekognitionIds) {
            this._rekognitionIds = _rekognitionIds;
        }
        @DynamoDBAttribute(attributeName = "street")
        public String getStreet() {
            return _street;
        }

        public void setStreet(final String _street) {
            this._street = _street;
        }
        @DynamoDBIndexHashKey(attributeName = "username", globalSecondaryIndexName = "username1")
        public String getUsername() {
            return _username;
        }

        public void setUsername(final String _username) {
            this._username = _username;
        }
        @DynamoDBAttribute(attributeName = "zip_code")
        public String getZipCode() {
            return _zipCode;
        }

        public void setZipCode(final String _zipCode) {
            this._zipCode = _zipCode;
        }

    }
