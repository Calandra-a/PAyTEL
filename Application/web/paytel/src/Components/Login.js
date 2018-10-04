import React, { Component } from "react";
import { Auth } from "aws-amplify";

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      loading: false,
      email: "",
      password: ""
    };
  }

  submit = async event => {
    event.preventDefault();

    this.setState({ loading: true });

    try {
      await Auth.signIn(this.state.email, this.state.password);

      //Need to indicate authentication and redirect after sign in here.
      //this.props.authenticated(true);
      //this.props.history.push("/");
    } catch (e) {
      alert(e.message);
      this.setState({ isLoading: false });
    }
  };
}
