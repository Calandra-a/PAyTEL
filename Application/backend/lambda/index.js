const AWS = require("aws-sdk");
const rek = new AWS.Rekognition({apiVersion: '2016-06-27'});


'use strict';
var globalContext = null;

exports.handler = function(event, context, callback) {
    //console.log(event);
    var requestBody = JSON.parse(event.body);
    //var requestBody = event.body;
    
    globalContext = context;
    var imgStr = requestBody.image;
    imgStr = imgStr.trim().replace(/^(data:image\/(png|jpeg);base64,)/,"");
    var user_id = requestBody.userID;
    var face_pose = requestBody.pose;
    
   const rekParams = {
        CollectionId: 'paytel-facial-collection',
        Image: {
            Bytes: new Buffer(imgStr, 'base64'),
        }
    };
    
     rek.indexFaces(rekParams, function(err, data){
            if(err){
                console.log(JSON.stringify(err));
                 Response({
                message: "face not detected",
                error: JSON.stringify(err)
            },500, globalContext);
            }else{
                if(data.FaceRecords[0] === undefined){
                     Response({
                        message: "face not detected",
                        error: JSON.stringify(err)
                    },500, globalContext);
                }else{
                    console.log(data.FaceRecords[0].Face.FaceId);
                     AddFaceIDtoDB(data.FaceRecords[0].Face.FaceId, user_id);
                }
            }
        });
};

function AddFaceIDtoDB(faceID,userID){
     var faceIDColl = [];
        faceIDColl.push(faceID);
    var docClient = new AWS.DynamoDB.DocumentClient();
     var DBparams = {
        TableName:"paytel-mobilehub-2098009603-user-data",
        Key:{
            "userId": userID
        },
        UpdateExpression: "add rekognition_ids  :r",
        ExpressionAttributeValues:{
            ":r":docClient.createSet([faceID.toString()])
        },
        ReturnValues:"UPDATED_NEW"
    };
    
    docClient.update(DBparams, function(err, data) {
        if (err) {
            console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
            Response({
                message: "DB could not be updated",
                user: userID.toString(),
                error: JSON.stringify(err)
            },400, globalContext);
        } 
            console.log("UpdateItem succeeded:", JSON.stringify(data, null, 2));
            Response({
                message: "User authenticated and added"
            },200, globalContext);
        
    });
}

function Response(responseBody, responseCode, context){
    // For demonstration purposes, we'll just echo these values back to the client

    var response = {
        statusCode: responseCode,
        headers: {
            "x-custom-header" : "custom header value"
        },
        body: JSON.stringify(responseBody)
    };
    console.log("response: " + JSON.stringify(response));
    context.succeed(response);
}