import React from "react";
import { Route, Switch } from "react-router-dom";
import Dashboard from "./Components/Dashboard";
import Login from "./Components/Login";
import NotFound from "./Components/NotFound";
import AppliedRoute from "./Components/AppliedRoute";

export default ({ childProps }) => (
  <Switch>
    <AppliedRoute path="/" exact component={Dashboard} props={childProps} />
    <AppliedRoute path="/login" exact component={Login} props={childProps} />

    <Route component={NotFound} />
  </Switch>
);
