import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  const data = event.queryStringParameters;
  var expression = "";
  var vals = {};

  if (data) {
    if (data.u) {
      expression = expression.concat("username = :username");
      vals[":username"] = data.u;
    }

    if (data.f) {
      expression = expression.concat("first_name = :first_name");
      vals[":first_name"] = data.f;
    }

    if (data.l) {
      expression = expression.concat("last_name = :last_name");
      vals[":last_name"] = data.l;
    }
  }

  const params =
    expression === ""
      ? {
          TableName: "paytel-mobilehub-2098009603-user-data"
          //ProjectionExpression:
        }
      : {
          TableName: "paytel-mobilehub-2098009603-user-data",
          FilterExpression: expression,
          ExpressionAttributeValues: vals
          //ProjectionExpression:
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
