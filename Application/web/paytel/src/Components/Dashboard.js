import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import { withStyles } from "@material-ui/core/styles";
import Drawer from "@material-ui/core/Drawer";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import NotFound from "./Pages/NotFound";
import logo_paytel from "../Resources/Images/logo_paytel.png";
import { Route, Switch } from "react-router-dom";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import ClearIcon from "@material-ui/icons/Clear";
import AttachMoneyIcon from "@material-ui/icons/AttachMoney";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import HomeIcon from "@material-ui/icons/Home";
import FlagIcon from "@material-ui/icons/Flag";
import ListIcon from "@material-ui/icons/List";
import { Auth } from "aws-amplify";
import Transaction from "./Pages/Transaction";
import User from "./Pages/User";
import Home from "./Pages/Home";
import EnhancedTable from "./Pages/EnhancedTable";
import DialogSelect from "./Pages/DatabaseLookup";
import Tooltip from "@material-ui/core/Tooltip";
import AppliedRoute from "./AppliedRoute";

const drawerWidth = 240;

const styles = theme => ({
  root: {
    flexGrow: 1,
    minHeight: "100vh",
    height: "100%",
    zIndex: 1,
    overflow: "hidden",
    position: "relative",
    display: "flex"
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    })
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  menuButton: {
    marginLeft: 12,
    marginRight: 36
  },
  hide: {
    display: "none"
  },
  drawerPaper: {
    position: "relative",
    whiteSpace: "nowrap",
    width: drawerWidth,
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  drawerPaperClose: {
    overflowX: "hidden",
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    }),
    minWidth: theme.spacing.unit * 7,
    width: theme.spacing.unit * 7,
    [theme.breakpoints.up("sm")]: {
      width: theme.spacing.unit * 9
    }
  },
  toolbar: {
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: "0 8px",
    ...theme.mixins.toolbar
  },
  content: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.default,
    padding: theme.spacing.unit * 3
  }
});

class Dashboard extends React.Component {
  state = {
    open: false
  };

  handleClickOpen = () => {
    this.select.handleClickOpen();
  };

  handleDrawerOpen = () => {
    this.setState({ open: true });
  };

  handleDrawerClose = () => {
    this.setState({ open: false });
  };

  handleLogout = async event => {
    await Auth.signOut();
    this.props.userHasAuthenticated(false);
    this.props.history.push("/login");
  };

  render() {
    const { classes, theme } = this.props;

    return (
      <div className={classes.root}>
        <AppBar
          position="absolute"
          className={classNames(
            classes.appBar,
            this.state.open && classes.appBarShift
          )}
        >
          <Toolbar disableGutters={!this.state.open}>
            <IconButton
              color="inherit"
              aria-label="Open drawer"
              onClick={this.handleDrawerOpen}
              className={classNames(
                classes.menuButton,
                this.state.open && classes.hide
              )}
            >
              <MenuIcon />
            </IconButton>
            <img src={logo_paytel} alt="PAyTEL" height="65" width="auto" />
          </Toolbar>
        </AppBar>
        <Drawer
          variant="permanent"
          classes={{
            paper: classNames(
              classes.drawerPaper,
              !this.state.open && classes.drawerPaperClose
            )
          }}
          open={this.state.open}
        >
          <div className={classes.toolbar}>
            <IconButton onClick={this.handleDrawerClose}>
              {theme.direction === "rtl" ? (
                <ChevronRightIcon />
              ) : (
                <ChevronLeftIcon />
              )}
            </IconButton>
          </div>
          <Divider />
          <List>
            <Tooltip
              title="Home"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem
                button
                onClick={() => {
                  this.props.history.push("/");
                }}
              >
                <ListItemIcon>
                  <HomeIcon />
                </ListItemIcon>
                <ListItemText primary="Home" />
              </ListItem>
            </Tooltip>
          </List>
          <Divider />
          <List>
            <Tooltip
              title="Manage Users"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem
                button
                onClick={() => {
                  this.props.history.push("/users");
                }}
              >
                <ListItemIcon>
                  <AccountCircleIcon />
                </ListItemIcon>
                <ListItemText primary="Manage Users" />
              </ListItem>
            </Tooltip>
            <Tooltip
              title="Manage Transactions"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem
                button
                onClick={() => {
                  this.props.history.push("/transactions");
                }}
              >
                <ListItemIcon>
                  <AttachMoneyIcon />
                </ListItemIcon>
                <ListItemText primary="Manage Transactions" />
              </ListItem>
            </Tooltip>
            <Tooltip
              title="Flagged Transactions"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem
                button
                onClick={() => {
                  this.props.history.push("/transactions/flagged");
                }}
              >
                <ListItemIcon>
                  <FlagIcon />
                </ListItemIcon>
                <ListItemText primary="Flagged Transactions" />
              </ListItem>
            </Tooltip>
            <Tooltip
              title="Database Lookup"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem
                button
                onClick={() => {
                  this.select.handleClickOpen();
                }}
              >
                <ListItemIcon>
                  <ListIcon />
                </ListItemIcon>
                <ListItemText primary="Database Lookup" />
              </ListItem>
            </Tooltip>
          </List>
          <Divider />
          <List>
            <Tooltip
              title="Log Out"
              placement="right"
              enterDelay={150}
              leaveDelay={150}
              disableFocusListener={this.state.open}
              disableHoverListener={this.state.open}
              disableTouchListener={this.state.open}
            >
              <ListItem button onClick={this.handleLogout}>
                <ListItemIcon>
                  <ClearIcon />
                </ListItemIcon>
                <ListItemText primary="Log Out" />
              </ListItem>
            </Tooltip>
          </List>
        </Drawer>
        <main className={classes.content}>
          <div className={classes.toolbar} />
          <DialogSelect onRef={ref => (this.select = ref)} />
          <Switch>
            <Route exact path="/" component={Home} />
            <AppliedRoute
              exact
              path="/transactions/flagged"
              component={EnhancedTable}
              props={{ state: "flagged" }}
            />
            <Route exact path="/transaction/:id" component={Transaction} />
            <Route exact path="/transactions" component={EnhancedTable} />
            <Route exact path="/user/:id" component={User} />
            <AppliedRoute
              exact
              path="/users"
              component={EnhancedTable}
              props={{ state: "user" }}
            />
            <Route component={NotFound} />
          </Switch>
        </main>
      </div>
    );
  }
}

Dashboard.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, { withTheme: true })(Dashboard);
