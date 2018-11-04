import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = JSON.parse(event.body);
  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    Key: {
      transaction_id: event.pathParameters.transaction_id
    },
    UpdateExpression: "SET transaction_status = :status",
    ExpressionAttributeValues: {
      ":status": data.flag ? "flagged" : "confirm"
    },
    ReturnValues: "ALL_NEW"
  };

  try {
    const result = await dynamoDbLib.call("update", params);
    callback(null, success({ status: true }));
  } catch (e) {
    console.log(e);
    callback(null, failure({ status: false }));
  }
}
