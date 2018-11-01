import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { API } from "aws-amplify";

const styles = theme => ({
  root: {
    ...theme.mixins.gutters(),
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 2
  }
});

class User extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true
    };
  }

  async componentDidMount() {
    try {
      const user = await this.user();
      this.setState({ user });
    } catch (e) {
      alert(e);
    }

    this.setState({ isLoading: false });
  }

  user() {
    return API.get("admin", "/users/".concat(this.props.match.params.id));
  }

  render() {
    const { classes } = this.props;
    const { user } = this.state;

    return (
      !this.state.isLoading && (
        <div>
          <Paper className={classes.root} elevation={1}>
            <Typography variant="headline" component="h3">
              User: {user.username}
            </Typography>
            <Typography variant="headline" component="h3">
              User # {user.userId}
            </Typography>
            <Typography component="p">First Name: {user.first_name}</Typography>
            <Typography component="p">Last Name: {user.last_name}</Typography>
          </Paper>
        </div>
      )
    );
  }
}

User.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(User);
