import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    FilterExpression: "begins_with(transaction_status, :status)",
    ExpressionAttributeValues: {
      ":status": "flagged_"
    },
    ProjectionExpression:
      "transaction_id, time_created, buyer_username, seller_username, transaction_status"
  };

  try {
    const result = await dynamoDbLib.call("scan", params);
    callback(null, success(result.Items));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
