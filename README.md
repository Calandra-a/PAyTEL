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
