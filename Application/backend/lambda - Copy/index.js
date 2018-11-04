const AWS = require("aws-sdk");
const rek = new AWS.Rekognition({apiVersion: '2016-06-27'});

var globalContext = null;
var totalOperations = 0;
var match_found = false;

exports.handler = function(event, context, callback) {
   globalContext = context;
   totalOperations = 0;

    
   var requestBody = JSON.parse(event.body);
   //return Response(200, event.body,context);
    //var requestBody = event.body;
 
    var imgStr = requestBody.image;
    imgStr = imgStr.trim().replace(/^(data:image\/(png|jpeg);base64,)/,"");
    var user_id = requestBody.userID;
    
    const rekParams = {
        CollectionId: 'paytel-facial-collection',
        Image: {
            Bytes: new Buffer(imgStr, 'base64'),
        }
    };
 
    rek.indexFaces(rekParams, function(err, data){
            if(err){
                console.log(JSON.stringify(err));
                    
            }else{
                 console.log(data.FaceRecords[0].Face.FaceId);
                 if(data.FaceRecords[0] !== undefined){
                    CompareFace(data.FaceRecords[0].Face.FaceId, user_id);
                 }else{
                     return Response(400, {message: "Facial index error"},globalContext);
                 }
            }
        });
};

function CompareFace(faceID, user_id){
    var DBparams = {
    TableName:"paytel-mobilehub-2098009603-user-data",
        Key:{
            "userId": user_id
        }
    };
    var rek_params = {
          CollectionId: "paytel-facial-collection", 
          FaceId: faceID, 
          FaceMatchThreshold: 20, 
          MaxFaces: 5
    };
     
     rek.searchFaces(rek_params, function(err, data){
         if(err){
            return Response(400, {message: "Facial match error"},globalContext);
         }else{
             if(data.FaceMatches[0] !== null){
                MatchFaceToUser(data.FaceMatches, DBparams, user_id, faceID);
             }
         }
     }); 
}

 function MatchFaceToUser(FaceMatches, DBparams, userID,new_face_id){
    var docClient = new AWS.DynamoDB.DocumentClient();
    
    docClient.get(DBparams, function(err, data) {
    if (err) {
        console.error("Unable to read item. Error JSON:", JSON.stringify(err, null, 2));
        return Response(400, {message: "User could not be found"},globalContext);
    } else {
        match_found = false;        
        data.Item.rekognition_ids.values.forEach(function(id, index1){

            FaceMatches.forEach(function(matched_id, index2){
                if(id === matched_id.Face.FaceId){
                    match_found = true;
                    console.log('match found');
                    /*var DBUpdateParams = {
                        TableName:"paytel-mobilehub-2098009603-user-data",
                        Key:{
                            "userId": userID
                        },
                        UpdateExpression: "add rekognition_ids = :r",
                        ExpressionAttributeValues:{
                            ":r":docClient.createSet([new_face_id.toString()])
                        },
                        ReturnValues:"NONE"
                    };
                    
                    docClient.update(DBUpdateParams, function(err, data) {
                        if (err) {
                            console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
                            return Response(400, {message: "DB could not be updated"},globalContext);
                        } else {
                            console.log("UpdateItem succeeded:", JSON.stringify(data, null, 2));
                            return Response(200, {message: "User authenticated"},globalContext);
                        }
                    });*/
                    return Response(200, {message: "User authenticated"},globalContext);
                }
                endOfLoop(data.Item.rekognition_ids.values.length, FaceMatches.length);
            });
            
        });
        
    }
});

 }
 function Response(responseCode, responseBody, context){
    var response = {
        statusCode: responseCode,
        headers: {
            "x-custom-header" : "facial",
            "Access-Control-Allow-Origin" : "*"
        },
        body: JSON.stringify(responseBody)
    };
    console.log("response: " + JSON.stringify(response));
    return context.succeed(response);
}

function endOfLoop(lengthLoop1, lengthLoop2){
    totalOperations++;
    
    if(totalOperations === (lengthLoop1*lengthLoop2) && !match_found){
        //return Response(400, {message: "not this user"},globalContext);
                return Response(405, {message: "not this user",to: lengthLoop1,tl: match_found},globalContext);

    }
}