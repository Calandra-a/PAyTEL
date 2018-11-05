import React from "react";
import ReactDOM from "react-dom";
import Amplify from "aws-amplify";
import App from "./App";
import config from "./config";
import { BrowserRouter as Router } from "react-router-dom";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";

Amplify.configure({
  Auth: {
    mandatorySignIn: true,
    region: config.cognito.REGION,
    userPoolId: config.cognito.USER_POOL_ID,
    identityPoolId: config.cognito.IDENTITY_POOL_ID,
    userPoolWebClientId: config.cognito.APP_CLIENT_ID
  },
  API: {
    endpoints: [
      {
        name: "admin",
        endpoint: config.apiGateway.URL,
        region: config.apiGateway.REGION
      },
      {
        name: "api_pull",
        endpoint:
          "https://eoa9urrjyl.execute-api.us-east-1.amazonaws.com/Development/",
        region: config.apiGateway.REGION
      }
    ]
  }
});

const theme = createMuiTheme({
  palette: {
    type: "dark",
    primary: { main: "#FFFFFF" },
    secondary: { main: "#f7b271" }
  }
});

ReactDOM.render(
  <MuiThemeProvider theme={theme}>
    <Router>
      <App />
    </Router>
  </MuiThemeProvider>,
  document.getElementById("root")
);
