import React, { Component, Fragment } from "react";
import { Auth } from "aws-amplify";
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  withRouter,
  Switch
} from "react-router-dom";
import Dashboard from "./Components/Dashboard";
import Login from "./Components/Login";
import NotFound from "./Components/NotFound";

const Authentication = {
  isAuthenticated: false,
  authenticate() {
    this.isAuthenticated = true;
  },
  signout() {
    this.isAuthenticated = false;
  }
};

const LoginRoute = ({
  component: Component,
  authenticator: Authenticator,
  ...rest
}) => (
  <Route
    {...rest}
    render={props =>
      !Authentication.isAuthenticated ? (
        <Component {...props} authenticator={Authenticator} />
      ) : (
        <Redirect to="/" />
      )
    }
  />
);

const DashboardRoute = ({
  component: Component,
  authenticator: Authenticator,
  ...rest
}) => (
  <Route
    {...rest}
    render={props =>
      Authentication.isAuthenticated ? (
        <Component {...props} authenticator={Authenticator} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <LoginRoute
            path="/login"
            component={Login}
            authenticator={Authentication}
          />
          <DashboardRoute
            path="/"
            component={Dashboard}
            authenticator={Authentication}
          />
          <Route path="" component={NotFound} />
        </Switch>
      </Router>
    );
  }
}

export default App;
