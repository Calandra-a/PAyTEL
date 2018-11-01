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

class Transaction extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true
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
    const response = await API.put(
      "admin",
      "/transactions/".concat(this.props.match.params.id),
      typeof this.state.transaction.flag === "undefined"
        ? { body: { flag: "1" } }
        : { body: {} }
    );
    const transaction = await this.transaction();
    this.setState({ transaction });
    return response;
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
            <Typography component="p">Buyer: {transaction.buyer_id}</Typography>
            <Typography component="p">
              Seller: {transaction.seller_id}
            </Typography>
            <Typography component="p">
              Flag: {transaction.flag ? transaction.flag : "No flag"}
            </Typography>
            <Button className={classes.button} onClick={this.flag}>
              {transaction.flag ? "Unflag" : "Flag"}
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
