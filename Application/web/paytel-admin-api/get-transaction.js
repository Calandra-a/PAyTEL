import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    Key: {
      transaction_id: event.pathParameters.transaction_id
    },
    ProjectionExpression:
      "transaction_id, time_created, buyer_username, seller_username, transaction_status, note"
  };

  try {
    const result = await dynamoDbLib.call("get", params);
    if (result.Item) {
      callback(null, success(result.Item));
    } else {
      callback(
        null,
        failure({ status: false, error: "Transaction not found." })
      );
    }
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
