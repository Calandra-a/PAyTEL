import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const flag = "flagged_";

  const paramsGet = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    Key: {
      transaction_id: event.pathParameters.transaction_id
    },
    ProjectionExpression: "transaction_status"
  };

  var status;
  try {
    const resultGet = await dynamoDbLib.call("get", paramsGet);
    status = resultGet.Item.transaction_status;
  } catch (e) {
    callback(null, failure({ status: false }));
  }

  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    Key: {
      transaction_id: event.pathParameters.transaction_id
    },
    UpdateExpression: "SET transaction_status = :status",
    ExpressionAttributeValues: {
      ":status": status.startsWith(flag)
        ? status.replace(flag, "")
        : flag + status
    },
    ReturnValues: "ALL_NEW"
  };

  try {
    const result = await dynamoDbLib.call("update", params);
    callback(null, success({ status: true }));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
