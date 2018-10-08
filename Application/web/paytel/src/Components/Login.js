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
import logo_pt from "../Resources/Images/logo_pt.png";
import Typography from "@material-ui/core/Typography";
import Hidden from "@material-ui/core/Hidden";

const styles = theme => ({
  gridStyling: {
    height: "100vh",
    backgroundColor: "red",
    display: "flex",
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center"
  },
  gridStyling2: {
    height: "100vh",
    backgroundColor: "cyan",
    display: "flex",
    flexGrow: 1,
    justifyContent: "center",
    alignItems: "center"
  },
  paper: {
    margin: theme.spacing.unit * 2,
    textAlign: "center"
  },
  form: {
    margin: theme.spacing.unit * 2
  },
  img: {
    paddingTop: theme.spacing.unit * 4
  },
  button: {
    marginTop: theme.spacing.unit * 4
  }
});

class Login extends Component {
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

  render() {
    const { classes } = this.props;

    return (
      <div>
        <Grid container>
          <Hidden xsDown>
            <Grid item sm={7} className={classes.gridStyling2}>
              <Paper>sm=6</Paper>
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
              <form className={classes.form}>
                <FormControl margin="normal" required fullWidth>
                  <InputLabel htmlFor="email">Email Address</InputLabel>
                  <Input
                    id="email"
                    name="email"
                    autoComplete="email"
                    autoFocus
                  />
                </FormControl>
                <FormControl margin="normal" required fullWidth>
                  <InputLabel htmlFor="password">Password</InputLabel>
                  <Input
                    name="password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                  />
                </FormControl>
                <Button
                  type="submit"
                  fullWidth
                  variant="raised"
                  color="primary"
                  className={classes.button}
                >
                  Sign in
                </Button>
              </form>
            </Paper>
          </Grid>
        </Grid>
      </div>
    );
  }
}

Login.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles)(Login);
