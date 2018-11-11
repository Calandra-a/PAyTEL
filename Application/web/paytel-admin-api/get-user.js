import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const params = {
    TableName: "paytel-mobilehub-2098009603-user-data",
    IndexName: "username1",
    KeyConditionExpression: "username = :username",
    ExpressionAttributeValues: {
      ":username": event.pathParameters.user_id
    }
    //ProjectionExpression:
  };

  try {
    const result = await dynamoDbLib.call("query", params);
    if (result.Items[0]) {
      callback(null, success(result.Items[0]));
    } else {
      callback(null, failure({ status: false, error: "User not found." }));
    }
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
