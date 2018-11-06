import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = event.queryStringParameters;
  var expression = "";
  var vals = {};

  if (data) {
    if (data.t) {
      expression = expression.concat("transaction_id = :transaction_id");
      vals[":transaction_id"] = data.t;
    }

    if (data.u) {
      expression = expressionCheck(expression).concat(
        "(buyer_id = :buyer_id or seller_id = :seller_id)"
      );
      vals[":buyer_id"] = data.u;
      vals[":seller_id"] = data.u;
    } else {
      if (data.b) {
        expression = expressionCheck(expression).concat("buyer_id = :buyer_id");
        vals[":buyer_id"] = data.b;
      }

      if (data.s) {
        expression = expressionCheck(expression).concat(
          "seller_id = :seller_id"
        );
        vals[":seller_id"] = data.s;
      }
    }
  }

  const params = {
    TableName: "paytel-mobilehub-2098009603-transactions",
    FilterExpression: expression,
    ExpressionAttributeValues: vals,
    ProjectionExpression:
      "transaction_id, time_created, buyer_username, seller_username, transaction_status"
  };

  if (expression === "") {
    delete params.FilterExpression;
    delete params.ExpressionAttributeValues;
  }

  try {
    const result = await dynamoDbLib.call("scan", params);
    callback(null, success(result.Items));
  } catch (e) {
    callback(null, failure({ status: false }));
  }
}

function expressionCheck(expression) {
  if (expression) return expression.concat(" and ");
  else return expression;
}
