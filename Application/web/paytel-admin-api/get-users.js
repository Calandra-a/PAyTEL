import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = event.queryStringParameters;
  var expression = "";
  var vals = {};

  if (data) {
    if (data.u) {
      expression = expression.concat("userId = :user_id");
      vals[":user_id"] = data.u;
    }
  }

  const params =
    expression === ""
      ? {
          TableName: "paytel-mobilehub-2098009603-user-data"
        }
      : {
          TableName: "paytel-mobilehub-2098009603-user-data",
          FilterExpression: expression,
          ExpressionAttributeValues: vals
        };

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
