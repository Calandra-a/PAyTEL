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
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";

const Authentication = {
  isAuthenticated: true,
  authenticate(cb) {
    this.isAuthenticated = true;
    setTimeout(cb, 100);
  },
  signout(cb) {
    this.isAuthenticated = false;
    setTimeout(cb, 100);
  }
};

const DashboardRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      Authentication.isAuthenticated ? (
        <Component {...props} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {};
  }

  handleLogout = async event => {
    await Auth.signOut();

    this.userHasAuthenticated(false);
    this.props.history.push("/login");
  };

  render() {
    return (
      <MuiThemeProvider>
        <Router>
          <Switch>
            <Route path="/login" component={Login} />
            <DashboardRoute path="/" component={Dashboard} />
          </Switch>
        </Router>
      </MuiThemeProvider>
    );
  }
}

export default App;
