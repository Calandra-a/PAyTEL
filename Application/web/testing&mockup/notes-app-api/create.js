import uuid from "uuid";
import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = JSON.parse(event.body);
  const params = {
    TableName: "testing",
    Item: {
      //testId: event.requestContext.identity.cognitoIdentityId,
      testId: uuid.v1(),
      buyer: data.buyer,
      seller: data.seller,
      amount: data.amount,
      timeStamp: Date.now()
    }
  };

  try {
    await dynamoDbLib.call("put", params);
    callback(null, success(params.Item));
  } catch (e) {
    //console.log(e);
    callback(null, failure({ status: false }));
  }
}
