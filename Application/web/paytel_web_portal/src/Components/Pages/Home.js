import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";

const styles = theme => ({
  root: {
    ...theme.mixins.gutters(),
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 2,
    marginBottom: theme.spacing.unit * 2
  }
});

function Home(props) {
  const { classes } = props;

  return (
    <div>
      <Paper className={classes.root} elevation={1}>
        <Typography variant="headline" component="h3">
          Welcome to PAyTEL Admin Dashboard!
        </Typography>
        <br />
        <Typography component="p">
          You can flag transactions, search users, and monitor transactions!
        </Typography>

        <Typography component="p">
          Below is a briefing of database operations.
        </Typography>
      </Paper>

      <Paper className={classes.root} elevation={1}>
        <Typography variant="headline" component="h3">
          Manage Users
        </Typography>
        <br />
        <Typography component="p">
          The Manage Users page is used to display users returned from a database scan.
        </Typography>
        <br />
        <Typography component="p">
          Selecting a user allows for viewing of the user profile.
        </Typography>
        <br />
        <Typography component="p">
          By default, this page will return the entire Transactions database for viewing.
        </Typography>
      </Paper>

      <Paper className={classes.root} elevation={1}>
        <Typography variant="headline" component="h3">
          Manage Transactions
        </Typography>
        <br />
        <Typography component="p">
          The Manage Transactions page is used to display transactions returned from a database scan.
        </Typography>
        <br />
        <Typography component="p">
          Within the Manage Transactions page, it is possible to flag one or multiple transactions in a single sweep.
        </Typography>
        <br />
        <Typography component="p">
          Selecting a single transaction allows for viewing of the transaction profile.
        </Typography>
        <br />
        <Typography component="p">
          By default, this page will return the entire Users database for viewing.
        </Typography>
      </Paper>

      <Paper className={classes.root} elevation={1}>
        <Typography variant="headline" component="h3">
          Flagged Transactions
        </Typography>
        <br />
        <Typography component="p">
          The Flagged Transactions page shows all transactions in a table that are marked as flagged, no matter the stage of the transaction.
        </Typography>
      </Paper>

      <Paper className={classes.root} elevation={1}>
        <Typography variant="headline" component="h3">
          Database Lookup
        </Typography>
        <br />
        <Typography component="p">
          The Database Lookup is a popup dialog, allowing for scanning on both the User and Transaction tables from any page.
        </Typography>
        <br />
        <Typography component="p">
          For transactions, scans by transaction ID, buyer, seller (or both: user) are the available search fields.
        </Typography>
        <br />
        <Typography component="p">
          For users, scans by user ID, first name and last are the available search fields.
        </Typography>
      </Paper>
    </div>
  );
}

Home.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Home);
