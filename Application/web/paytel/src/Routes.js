import React from "react";
import { Switch } from "react-router-dom";
import Dashboard from "./Components/Dashboard";
import Login from "./Components/Login";
import PrivateRoute from "./Components/PrivateRoute";
import LoginRoute from "./Components/LoginRoute";

export default ({ childProps }) => (
  <Switch>
    <LoginRoute path="/login" component={Login} props={childProps} />
    <PrivateRoute path="/" component={Dashboard} props={childProps} />
  </Switch>
);
