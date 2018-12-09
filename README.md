# CSI-4999
CSI-4999 Project 9

This is a system that will consist of three pieces, a web portal that manages all transactions, a buyer mobile app for approval of transaction and a seller mobile  app  for  issuing  a  charge.  The  front  facing  mobile  phone  camera  will  be  used  for authenticating  biometric  identity  of the  buyer  when  approving  the  transaction.  Server  will  mediate  the messaging, manage user profile and transactions.

# Backend setup
The backend is build on Amazon Web Services (AWS). First you need to create and AWS account.

#### Step 1:
On aws.amazon.com login and go to the mobile hub service. Once here click import and open the project file in application/backend/mobile-hub-paytel.yml. This will create the api gateways, api's, database, lambda functions, and authentication. 

#### Step 2:
Upload the zip file located under application/backend/ to the coresponding lambda functions. 

#### Step 3: 
Download the awsconfiguration file for Android from the mobile hub project.
Replace the aws configuration file in the Android project located under res/raw/awsconfiguration.json

# Web portal setup

#### Step 1:
Open command prompt and go to the application/paytel_web_portal folder;

#### Step 2:
On the command prompt execute 
npm install
After this all the required dependencies will be installed.

#### Step 3:
On the command prompt execute 
npm start
This command will start the web portal on localhost:3000

#### Step 4: 
Login using the credentials;

# Android Application

#### Step 1:
Download and open Android studio

#### Step 2:
Open the Android project located in Application/Android
Android studio will prompt you to install all the dependencies.

#### Step 3:
Launch the application on a connected device or on a virtual device.
