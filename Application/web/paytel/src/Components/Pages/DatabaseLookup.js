import React, { Fragment } from "react";
import PropTypes from "prop-types";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Dialog from "@material-ui/core/Dialog";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import { withRouter } from "react-router";
import TextField from "@material-ui/core/TextField";
import { AppBar, Tabs, Tab } from "@material-ui/core";

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
    value: 0,
    transaction_id: "",
    user: "",
    buyer: "",
    seller: "",
    name_user: "",
    name_first: "",
    name_last: ""
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

  handleTabChange = (event, value) => {
    this.setState({ value });
  };

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleSubmit = () => {
    var search = "";

    this.setState({ transaction_id: this.state.transaction_id.trim() });
    this.setState({ user: this.state.user.trim() });
    this.setState({ buyer: this.state.buyer.trim() });
    this.setState({ seller: this.state.seller.trim() });
    this.setState({ name_user: this.state.name_user.trim() });
    this.setState({ name_first: this.state.name_first.trim() });
    this.setState({ name_last: this.state.name_last.trim() });

    if (this.state.value === 0) {
      if (this.state.transaction_id)
        search = search.concat("t=".concat(this.state.transaction_id.trim()));

      if (this.state.user) {
        if (search) search = search.concat("&");
        search = search.concat("u=".concat(this.state.user.trim()));
      } else {
        if (this.state.buyer) {
          if (search) search = search.concat("&");
          search = search.concat("b=".concat(this.state.buyer.trim()));
        }

        if (this.state.seller) {
          if (search) search = search.concat("&");
          search = search.concat("s=".concat(this.state.seller.trim()));
        }
      }
    } else {
      if (this.state.name_user) {
        search = search.concat("u=".concat(this.state.name_user.trim()));
      }

      if (this.state.name_first) {
        if (search) search = search.concat("&");
        search = search.concat("f=".concat(this.state.name_first.trim()));
      }

      if (this.state.name_last) {
        if (search) search = search.concat("&");
        search = search.concat("l=".concat(this.state.name_last.trim()));
      }
    }

    if (search) search = "?".concat(search);

    this.setState({ open: false });
    this.props.history.push(
      (this.state.value === 0 ? "/transactions" : "/users").concat(search)
    );
  };

  render() {
    const { classes } = this.props;
    const { value } = this.state;

    return (
      <div>
        <Dialog
          disableBackdropClick
          disableEscapeKeyDown
          open={this.state.open}
          onClose={this.handleClose}
        >
          <AppBar position="static">
            <Tabs value={value} onChange={this.handleTabChange}>
              <Tab label="Transactions" />
              <Tab label="Users" />
            </Tabs>
          </AppBar>

          {value === 0 && (
            <Fragment>
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
                    disabled={
                      this.state.buyer !== "" || this.state.seller !== ""
                    }
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
            </Fragment>
          )}
          {value === 1 && (
            <Fragment>
              <DialogTitle>Perform User Scan</DialogTitle>
              <DialogContent>
                <form className={classes.container}>
                  <TextField
                    label="Username"
                    className={classes.textField}
                    value={this.state.name_user}
                    onChange={this.handleChange("name_user")}
                    margin="normal"
                    disabled={
                      this.state.buyer !== "" || this.state.seller !== ""
                    }
                  />
                  <TextField
                    label="First Name"
                    className={classes.textField}
                    value={this.state.name_first}
                    onChange={this.handleChange("name_first")}
                    margin="normal"
                    disabled={this.state.user !== ""}
                  />
                  <TextField
                    label="Last Name"
                    className={classes.textField}
                    value={this.state.name_last}
                    onChange={this.handleChange("name_last")}
                    margin="normal"
                    disabled={this.state.user !== ""}
                  />
                </form>
              </DialogContent>
            </Fragment>
          )}
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
