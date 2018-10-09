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

const LoginRoute = ({
  isauthenticated: is,
  component: Component,
  signin: Signin,
  ...rest
}) => (
  <Route
    {...rest}
    render={props =>
      !is ? <Component {...props} signin={Signin} /> : <Redirect to="/" />
    }
  />
);

const DashboardRoute = ({
  isauthenticated: is,
  component: Component,
  signout: Signout,
  ...rest
}) => (
  <Route
    {...rest}
    render={props =>
      is ? <Component {...props} signout={Signout} /> : <Redirect to="/login" />
    }
  />
);

class App extends Component {
  constructor(props) {
    super(props);

    this.signin = this.signin.bind(this);
    this.signout = this.signout.bind(this);

    this.state = {
      isAuthenticated: false
    };
  }

  componentDidMount() {
    const is = sessionStorage.getItem("isAuthenticated");

    if (is) {
      try {
        this.setState({
          isAuthenticated: JSON.parse(sessionStorage.getItem("isAuthenticated"))
        });
      } catch (e) {}
    }
  }

  componentWillUnmount() {
    sessionStorage.setItem(
      "isAuthenticated",
      JSON.stringify(this.state.isAuthenticated)
    );
  }

  setIsAuthenticated(value) {
    this.setState({ isAuthenticated: value }, () => {
      sessionStorage.setItem(
        "isAuthenticated",
        JSON.stringify(this.state.isAuthenticated)
      );
    });
  }

  signin() {
    this.setIsAuthenticated(true);
    return <Redirect push to="/" />;
  }

  signout() {
    this.setIsAuthenticated(false);
    return <Redirect push to="/login" />;
  }

  render() {
    return (
      <Router>
        <Switch>
          <LoginRoute
            path="/login"
            isauthenticated={this.state.isAuthenticated}
            component={Login}
            signin={this.signin}
          />
          <DashboardRoute
            path="/"
            isauthenticated={this.state.isAuthenticated}
            component={Dashboard}
            signout={this.signout}
          />
          <Route path="" component={NotFound} />
        </Switch>
      </Router>
    );
  }
}

export default App;
