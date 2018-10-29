import uuid from "uuid";
import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = JSON.parse(event.body);
  const params = {
    TableName: "csi-mobilehub-447478737-transactions",
    Item: {
      transaction_id: uuid.v1(),
      buyer_id: data.buyer_id,
      seller_id: data.seller_id,
      buyer_username: data.buyer_username,
      seller_username: data.seller_username,
      transaction_status: data.transaction_status,
      status: data.status,
      amount: data.amount,
      note: data.note,
      time_created: Date.now()
    }
  };

  try {
    await dynamoDbLib.call("put", params);
    callback(null, success(params.Item));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}
