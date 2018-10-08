import React, { Component } from "react";

class NotFound extends Component {
  constructor(props) {
    super(props);
    console.log(props);
  }

  render() {
    return (
      <div>
        <h1>{this.props.message}</h1>
      </div>
    );
  }
}

export default NotFound;
