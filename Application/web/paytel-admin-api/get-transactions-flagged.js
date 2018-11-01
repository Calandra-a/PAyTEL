import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    FilterExpression: "attribute_exists(flag)"
  };

  try {
    const result = await dynamoDbLib.call("scan", params);
    callback(null, success(result.Items));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
