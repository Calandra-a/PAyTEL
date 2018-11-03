import React from "react";
import PropTypes from "prop-types";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import { withRouter } from "react-router";
import TextField from "@material-ui/core/TextField";

const styles = theme => ({
  container: {
    display: "flex",
    flexWrap: "wrap"
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200
  }
});

class DatabaseLookup extends React.Component {
  state = {
    open: false,
    transaction_id: "",
    user: "",
    buyer: "",
    seller: ""
  };

  componentDidMount() {
    this.props.onRef(this);
  }
  componentWillUnmount() {
    this.props.onRef(undefined);
  }

  handleChange = name => event => {
    this.setState({ [name]: event.target.value });
  };

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleSubmit = () => {
    var search = "";

    if (this.state.transaction_id)
      search = search.concat("t=".concat(this.state.transaction_id));

    if (this.state.user) {
      if (search) search = search.concat("&");
      search = search.concat("u=".concat(this.state.user));
    } else {
      if (this.state.buyer) {
        if (search) search = search.concat("&");
        search = search.concat("b=".concat(this.state.buyer));
      }

      if (this.state.seller) {
        if (search) search = search.concat("&");
        search = search.concat("s=".concat(this.state.seller));
      }
    }

    if (search) search = "?".concat(search);

    this.setState({ open: false });
    this.props.history.push("/transactions".concat(search));
  };

  render() {
    const { classes } = this.props;

    return (
      <div>
        <Dialog
          disableBackdropClick
          disableEscapeKeyDown
          open={this.state.open}
          onClose={this.handleClose}
        >
          <DialogTitle>Perform Transaction Scan</DialogTitle>
          <DialogContent>
            <form className={classes.container}>
              <TextField
                label="Transaction ID"
                className={classes.textField}
                value={this.state.transaction_id}
                onChange={this.handleChange("transaction_id")}
                margin="normal"
              />
              <TextField
                label="User"
                className={classes.textField}
                value={this.state.user}
                onChange={this.handleChange("user")}
                margin="normal"
                disabled={this.state.buyer !== "" || this.state.seller !== ""}
              />
              <TextField
                label="Buyer"
                className={classes.textField}
                value={this.state.buyer}
                onChange={this.handleChange("buyer")}
                margin="normal"
                disabled={this.state.user !== ""}
              />
              <TextField
                label="Seller"
                className={classes.textField}
                value={this.state.seller}
                onChange={this.handleChange("seller")}
                margin="normal"
                disabled={this.state.user !== ""}
              />
            </form>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose} color="primary">
              Cancel
            </Button>
            <Button onClick={this.handleSubmit} color="primary">
              Scan
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    );
  }
}

DatabaseLookup.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withRouter(withStyles(styles)(DatabaseLookup));
