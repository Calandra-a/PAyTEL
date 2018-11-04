import React, { Component } from "react";
import { Auth } from "aws-amplify";
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import logo_paytel from "../Resources/Images/logo_paytel.png";
import logo_pt from "../Resources/Images/logo_pt.png";
import Typography from "@material-ui/core/Typography";
import Hidden from "@material-ui/core/Hidden";
import CircularProgress from "@material-ui/core/CircularProgress";
import Snackbar from "@material-ui/core/Snackbar";

const styles = theme => ({
  button: {
    marginTop: theme.spacing.unit * 4
  },
  gridStyling: {
    height: "100vh",
    backgroundColor: "#f7b271",
    display: "flex",
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center"
  },
  gridStyling2: {
    height: "100vh",
    display: "flex",
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center"
  },
  form: {
    margin: theme.spacing.unit * 2
  },
  img: {
    marginTop: theme.spacing.unit * 4,
    backgroundColor: "white",
    borderRadius: "25%"
  },
  paper: {
    margin: theme.spacing.unit * 2,
    textAlign: "center"
  }
});

class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      open: false,
      loading: false,
      username: "",
      password: "",
      message: ""
    };
  }

  handleChange = event => {
    this.setState({
      [event.target.id]: event.target.value
    });
  };

  handleSubmit = async event => {
    event.preventDefault();

    this.setState({ loading: true });

    try {
      await Auth.signIn(this.state.username, this.state.password);
      this.props.userHasAuthenticated(true);
      this.props.history.push("/");
    } catch (e) {
      this.setState({ loading: false });
      this.setState({ message: e.message });
      this.setState({ open: true });
    }
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  render() {
    const { classes } = this.props;

    return (
      <div>
        <Grid container>
          <Hidden xsDown>
            <Grid item sm={7} className={classes.gridStyling2}>
              <img src={logo_paytel} alt="PAyTEL" height="100" width="auto" />
            </Grid>
          </Hidden>
          <Grid item sm={5} className={classes.gridStyling}>
            <Paper className={classes.paper}>
              <img
                src={logo_pt}
                alt="PAyTEL"
                height="65"
                width="auto"
                className={classes.img}
              />
              <Typography variant="headline">Please Log In.</Typography>
              <form className={classes.form} onSubmit={this.handleSubmit}>
                <FormControl
                  margin="normal"
                  value={this.state.username}
                  onChange={this.handleChange}
                  required
                  fullWidth
                >
                  <InputLabel htmlFor="username">Username</InputLabel>
                  <Input id="username" name="username" autoFocus />
                </FormControl>
                <FormControl
                  margin="normal"
                  value={this.state.password}
                  onChange={this.handleChange}
                  required
                  fullWidth
                >
                  <InputLabel htmlFor="password">Password</InputLabel>
                  <Input name="password" type="password" id="password" />
                </FormControl>
                <Button
                  type="submit"
                  fullWidth
                  variant="raised"
                  color="primary"
                  className={classes.button}
                >
                  {this.state.loading ? (
                    <CircularProgress
                      className={classes.progress}
                      color="secondary"
                    />
                  ) : (
                    "Sign in"
                  )}
                </Button>
              </form>
            </Paper>
          </Grid>
        </Grid>
        <Snackbar
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          open={this.state.open}
          onClose={this.handleClose}
          ContentProps={{
            "aria-describedby": "message-id"
          }}
          message={<span id="message-id">{this.state.message}</span>}
        />
      </div>
    );
  }
}

Login.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Login);
