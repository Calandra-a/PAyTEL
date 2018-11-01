import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const params = {
    TableName: "paytel-mobilehub-2098009603-user-data",
    Key: {
      userId: event.pathParameters.user_id
    }
  };

  try {
    const result = await dynamoDbLib.call("get", params);
    if (result.Item) {
      callback(null, success(result.Item));
    } else {
      callback(null, failure({ status: false, error: "User not found." }));
    }
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
