import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import { API } from "aws-amplify";
import MediaCard from "./Libs/MediaCard";
import Grid from "@material-ui/core/Grid";

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
      console.log(user.user_info)
      this.setState({ user: user.user_info });
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
          <Grid container spacing={24}>
            <Grid item xs={12} md={6}>
              <Paper className={classes.root} elevation={1}>
                <Typography variant="h5">
                  Profile Info
                </Typography>
                <br />
                <Typography component="p">Last, First Name: {user.last_name}, {user.first_name}</Typography>
                <Typography component="p">ID: {user.user_id}</Typography>
                <Typography component="p">Phone Number: {user.phone_number}</Typography>
                <br />
                <br />
                <Typography variant="h5">Address</Typography>
                <Typography component="p">Street: {user.address.street}</Typography>
                <Typography component="p">City, State: {user.address.city}, {user.address.state}</Typography>
                <Typography component="p">Zip Code: {user.address.zip_code}</Typography>
                <br />
                <Typography component="p">{user.address.street}, {user.address.city}, {user.address.state} {user.address.zip_code}</Typography>
                <br />
                <br />
                <Typography variant="h5">Credit Card</Typography>
                <Typography component="p">Name on Card: {user.credit_card.name_on_card}</Typography>
                <Typography component="p">Expires: {user.credit_card.expiration_date}</Typography>
                <Typography component="p">Last Four: {user.credit_card.number}</Typography>
              </Paper>
            </Grid>
            <Grid item xs={12} md={6}>
              <MediaCard view title={user.username} link="https://s3.amazonaws.com/paytel-userfiles-mobilehub-2098009603/public/userprofiles/us-east-1%3Ad5c1a7a3-45f7-4358-bbae-40c2449396fe/profilepic.jpg"></MediaCard>
            </Grid>
          </Grid>
        </div>
      )
    );
  }
}

User.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(User);
