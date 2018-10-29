import * as dynamoDbLib from "./libs/dynamodb-lib";
import { success, failure } from "./libs/response-lib";

export async function main(event, context, callback) {
  var expression = "";
  var vals = {};

  if (event.pathParameters.transactionID) {
    expression = expression.concat("transactionID = :transactionID");
    vals[":transactionID"] = event.pathParameters.transactionID;
  }

  if (event.pathParameters.buyerID) {
    expression = expressionCheck(expression).concat("buyerID = :buyerID");
    vals[":buyerID"] = event.pathParameters.buyerID;
  }

  if (event.pathParameters.sellerID) {
    expression = expressionCheck(expression).concat("sellerID = :sellerID");
    vals[":sellerID"] = event.pathParameters.sellerID;
  }

  console.log(expression);
  console.log(vals);

  const params =
    expression === ""
      ? {
          TableName: "transactions"
        }
      : {
          TableName: "transactions",
          FilterExpression: expression,
          ExpressionAttributeValues: vals
        };

  console.log(params.FilterExpression);

  try {
    const result = await dynamoDbLib.call("scan", params);
    callback(null, success(result.Items));
  } catch (e) {
    console.log(e);
    callback(null, failure({ status: false }));
  }
}

function expressionCheck(expression) {
  if (expression) return expression.concat(" and ");
  else return expression;
}
