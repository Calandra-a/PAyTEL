import React from "react";
import { Route, Switch } from "react-router-dom";
import Dashboard from "./Components/Dashboard";
import Login from "./Components/Login";
import NotFound from "./Components/NotFound";
import AppliedRoute from "./Components/AppliedRoute";
import PrivateRoute from "./Components/PrivateRoute";
import LoginRoute from "./Components/LoginRoute";

export default ({ childProps }) => (
  <Switch>
    <LoginRoute path="/login" component={Login} props={childProps} />
    <PrivateRoute
      path="/transactions"
      component={Dashboard}
      props={childProps}
    />
    <PrivateRoute path="/" component={Dashboard} props={childProps} />
  </Switch>
);
