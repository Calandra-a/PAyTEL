import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = JSON.parse(event.body);
  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    Key: {
      transaction_id: event.pathParameters.transaction_id
    },
    UpdateExpression: data.flag ? "SET flag = :flag" : "REMOVE flag",
    ExpressionAttributeValues: {
      ":flag": data.flag
    },
    ReturnValues: "ALL_NEW"
  };

  if (!data.flag) delete params.ExpressionAttributeValues;

  try {
    const result = await dynamoDbLib.call("update", params);
    callback(null, success({ status: true }));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
