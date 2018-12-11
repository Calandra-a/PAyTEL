import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import { API } from "aws-amplify";
import Grid from "@material-ui/core/Grid"
import MediaCard from "./Libs/MediaCard"

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
      this.props.history.push("/404");
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

  handleBuyer = () => {
    this.props.history.push("/user/".concat(this.state.transaction.buyer_username));
  }

  handleSeller = () => {
    this.props.history.push("/user/".concat(this.state.transaction.seller_username));
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
          <Grid container spacing={24}>
            <Grid item xs={12} md={6}>
              <Paper className={classes.root} elevation={1}>
                <Typography variant="headline">
                  Transaction Between
                </Typography>
                <br />
                <Typography variant="h5">
                  The Buyer: {transaction.buyer_username}
                </Typography>
                <br />
                <Typography variant="h5">
                  The Seller: {transaction.seller_username}
                </Typography>
                <br />
                <Typography component="p">
                  Started at the time of: {transaction.time_created}
                </Typography>
                <Typography component="p">
                  Current Status: {transaction.transaction_status}
                </Typography>
                <br />
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
            </Grid>
            <Grid item xs={12} md={6}>
              <MediaCard title="Confirmation" link={"https://s3.amazonaws.com/paytel-userfiles-mobilehub-2098009603/public/transactions/" + transaction.transaction_id + "/verified.jpg"}></MediaCard>
            </Grid>
            <Grid item xs={12} md={6}>
              <MediaCard view={this.handleBuyer} title={transaction.buyer_username} link={"https://s3.amazonaws.com/paytel-userfiles-mobilehub-2098009603/public/userprofiles/" + transaction.buyer_id.replace(":","%3A") + "/profilepic.jpg"}></MediaCard>
            </Grid>
            <Grid item xs={12} md={6}>
              <MediaCard view={this.handleSeller} title={transaction.seller_username} link={"https://s3.amazonaws.com/paytel-userfiles-mobilehub-2098009603/public/userprofiles/" + transaction.seller_id.replace(":","%3A") + "/profilepic.jpg"}></MediaCard>
            </Grid>
          </Grid>
        </div>
      )
    );
  }
}

Transaction.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(Transaction);
