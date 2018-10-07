import React from "react";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import MailIcon from "@material-ui/icons/Mail";
import AttachMoneyIcon from "@material-ui/icons/AttachMoney";
import AccountCircleIcon from "@material-ui/icons/AccountCircle";
import SettingsIcon from "@material-ui/icons/Settings";
import HomeIcon from "@material-ui/icons/Home";
import ClearIcon from "@material-ui/icons/Clear";
import FlagIcon from "@material-ui/icons/Flag";
import ListIcon from "@material-ui/icons/List";
import { Link } from "react-router-dom";

export const navigationListItems = (
  <div>
    <Link to="/">
      <ListItem button>
        <ListItemIcon>
          <HomeIcon />
        </ListItemIcon>
        <ListItemText primary="Home" />
      </ListItem>
    </Link>
  </div>
);

export const adminListItems = (
  <div>
    <ListItem button>
      <ListItemIcon>
        <MailIcon />
      </ListItemIcon>
      <ListItemText primary="View Complaints" />
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <AccountCircleIcon />
      </ListItemIcon>
      <ListItemText primary="Manage Users" />
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <AttachMoneyIcon />
      </ListItemIcon>
      <ListItemText primary="Manage Transactions" />
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <FlagIcon />
      </ListItemIcon>
      <ListItemText primary="Flag Transaction" />
    </ListItem>
    <Link to="/results">
      <ListItem button>
        <ListItemIcon>
          <ListIcon />
        </ListItemIcon>
        <ListItemText primary="Database Lookup" />
      </ListItem>
    </Link>
  </div>
);

export const accountListItems = (
  <div>
    <ListItem button>
      <ListItemIcon>
        <SettingsIcon />
      </ListItemIcon>
      <ListItemText primary="Settings" />
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <ClearIcon />
      </ListItemIcon>
      <ListItemText primary="Log Out" />
    </ListItem>
  </div>
);
