import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import { API } from "aws-amplify";

const styles = theme => ({
  root: {
    ...theme.mixins.gutters(),
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 2
  }
});

class Transaction extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      isFlagging: false
    };

    this.flag = this.flag.bind(this);
  }

  async componentDidMount() {
    try {
      const transaction = await this.transaction();
      this.setState({ transaction });
    } catch (e) {
      alert(e);
    }

    this.setState({ isLoading: false });
  }

  async flag() {
    this.setState({ isFlagging: true });
    await API.put("admin", "/transactions/".concat(this.props.match.params.id));
    const transaction = await this.transaction();
    this.setState({ transaction, isFlagging: false });
  }

  transaction() {
    return API.get(
      "admin",
      "/transactions/".concat(this.props.match.params.id)
    );
  }

  render() {
    const { classes } = this.props;
    const { transaction } = this.state;

    return (
      !this.state.isLoading && (
        <div>
          <Paper className={classes.root} elevation={1}>
            <Typography variant="headline" component="h3">
              Transaction # {transaction.transaction_id}
            </Typography>
            <Typography component="p">
              Time of Creation: {transaction.time_created}
            </Typography>
            <Typography component="p">
              Buyer: {transaction.buyer_username}
            </Typography>
            <Typography component="p">
              Seller: {transaction.seller_username}
            </Typography>
            <Typography component="p">
              Status: {transaction.transaction_status}
            </Typography>
            <Button
              className={classes.button}
              onClick={this.flag}
              disabled={this.state.isFlagging}
            >
              {this.state.isFlagging ? (
                <CircularProgress
                  className={classes.progress}
                  color="primary"
                />
              ) : transaction.transaction_status.startsWith("flagged_") ? (
                "Unflag"
              ) : (
                "Flag"
              )}
            </Button>
          </Paper>
        </div>
      )
    );
  }
}

Transaction.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Transaction);
