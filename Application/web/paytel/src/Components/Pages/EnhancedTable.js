import React, { Fragment } from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TablePagination from "@material-ui/core/TablePagination";
import TableRow from "@material-ui/core/TableRow";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Paper from "@material-ui/core/Paper";
import Checkbox from "@material-ui/core/Checkbox";
import IconButton from "@material-ui/core/IconButton";
import Tooltip from "@material-ui/core/Tooltip";
import FlagIcon from "@material-ui/icons/Flag";
import Build from "@material-ui/icons/Build";
import CircularProgress from "@material-ui/core/CircularProgress";
import LinearProgress from "@material-ui/core/LinearProgress";
import Grow from "@material-ui/core/Grow";
import Zoom from "@material-ui/core/Zoom";
import { lighten } from "@material-ui/core/styles/colorManipulator";
import { API } from "aws-amplify";

const table_user = "user";
const table_flagged = "flagged";

let counter = 0;

function createUser(username, name_first, name_last) {
  counter += 1;
  return {
    id: counter,
    username,
    name_first,
    name_last
  };
}

function createTransaction(
  date,
  transaction_id,
  buyer,
  seller,
  transaction_status,
  note
) {
  counter += 1;
  return {
    id: counter,
    date,
    transaction_id,
    buyer,
    seller,
    transaction_status,
    note
  };
}

function desc(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function stableSort(array, cmp) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
}

function getSorting(order, orderBy) {
  return order === "desc"
    ? (a, b) => desc(a, b, orderBy)
    : (a, b) => -desc(a, b, orderBy);
}

const rows = [
  {
    id: "date",
    numeric: false,
    disablePadding: true,
    label: "Date"
  },
  {
    id: "transaction_id",
    numeric: false,
    disablePadding: false,
    label: "Transaction ID"
  },
  {
    id: "buyer",
    numeric: false,
    disablePadding: false,
    label: "Buyer Username"
  },
  {
    id: "seller",
    numeric: false,
    disablePadding: false,
    label: "Seller Username"
  },
  {
    id: "transaction_status",
    numeric: false,
    disablePadding: false,
    label: "Transaction Status"
  },
  {
    id: "note",
    numeric: false,
    disablePadding: false,
    label: "Note"
  }
];

const user_rows = [
  {
    id: "username",
    numeric: false,
    disablePadding: false,
    label: "Username"
  },
  {
    id: "name_first",
    numeric: false,
    disablePadding: false,
    label: "First Name"
  },
  {
    id: "name_last",
    numeric: false,
    disablePadding: false,
    label: "Last Name"
  }
];

class EnhancedTableHead extends React.Component {
  createSortHandler = property => event => {
    this.props.onRequestSort(event, property);
  };

  render() {
    const {
      onSelectAllClick,
      order,
      orderBy,
      numSelected,
      rowCount,
      rows
    } = this.props;

    return (
      <TableHead>
        <TableRow>
          <TableCell padding="checkbox">
            <Checkbox
              indeterminate={numSelected > 0 && numSelected < rowCount}
              checked={numSelected === rowCount}
              onChange={onSelectAllClick}
            />
          </TableCell>
          {rows.map(row => {
            return (
              <TableCell
                key={row.id}
                numeric={row.numeric}
                padding={row.disablePadding ? "none" : "default"}
                sortDirection={orderBy === row.id ? order : false}
              >
                <Tooltip
                  title="Sort"
                  placement={row.numeric ? "bottom-end" : "bottom-start"}
                  enterDelay={300}
                >
                  <TableSortLabel
                    active={orderBy === row.id}
                    direction={order}
                    onClick={this.createSortHandler(row.id)}
                  >
                    {row.label}
                  </TableSortLabel>
                </Tooltip>
              </TableCell>
            );
          }, this)}
        </TableRow>
      </TableHead>
    );
  }
}

EnhancedTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.string.isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired
};

const toolbarStyles = theme => ({
  root: {
    paddingRight: theme.spacing.unit,
    transition: "background-color 250ms"
  },
  highlight:
    theme.palette.type === "light"
      ? {
          color: theme.palette.secondary.main,
          backgroundColor: lighten(theme.palette.secondary.light, 0.85)
        }
      : {
          color: theme.palette.text.primary,
          backgroundColor: theme.palette.secondary.dark
        },
  spacer: {
    flex: "1 1 auto"
  },
  actions: {
    color: theme.palette.text.secondary,
    display: "flex",
    flexDirection: "row",
    alignItems: "center"
  },
  title: {
    flex: "0 0 auto"
  }
});

let EnhancedTableToolbar = props => {
  const { numSelected, classes, state } = props;

  return (
    <Toolbar
      className={classNames(classes.root, {
        [classes.highlight]: numSelected > 0
      })}
    >
      <div className={classes.title}>
        {numSelected > 0 ? (
          <Typography color="inherit" variant="title">
            {numSelected} selected
          </Typography>
        ) : (
          <Typography variant="title" id="tableTitle">
            {state === table_user ? "Users" : state === table_flagged ? "Flagged Transactions" : "Transactions"}
          </Typography>
        )}
      </div>
      <div className={classes.spacer} />
      <div className={classes.actions}>
        <Tooltip title={"Manage " + (state === table_user ? "User" : "Transaction")}>
          <Grow in={numSelected === 1}>
            <IconButton
              aria-label={"Manage " + (state === table_user ? "User" : "Transaction")}
              onClick={props.handleManage}
              disabled={numSelected !== 1}
            >
              <Build />
            </IconButton>
          </Grow>
        </Tooltip>
        {props.isFlagging ? (
          <CircularProgress className={classes.progress} color="primary" />
        ) : numSelected > 0 && state !== table_user ? (
          <Tooltip title="Flag Transaction(s)">
            <IconButton aria-label="Flag Transaction(s)" onClick={props.flag}>
              <FlagIcon />
            </IconButton>
          </Tooltip>
        ) : <Fragment/>}
      </div>
    </Toolbar>
  );
};

EnhancedTableToolbar.propTypes = {
  classes: PropTypes.object.isRequired,
  numSelected: PropTypes.number.isRequired
};

EnhancedTableToolbar = withStyles(toolbarStyles)(EnhancedTableToolbar);

const styles = theme => ({
  root: {
    width: "100%",
    marginTop: theme.spacing.unit * 3
  },
  table: {
    minWidth: 1020
  },
  tableWrapper: {
    overflowX: "auto"
  },
  loader: {
    marginTop: theme.spacing.unit * 3
  }
});

class EnhancedTable extends React.Component {
  state = {
    order: "asc",
    orderBy: "date",
    selected: [],
    data: [],
    page: 0,
    rowsPerPage: 5,
    isLoading: true,
    isLoadingRows: true,
    isFlagging: false,
    rows: []
  };

  async componentDidMount() {
    await this.scans();
    this.setState({ isLoading: false });
    this.setState({ isLoadingRows: false });
  }

  async componentWillReceiveProps(nextProps) {
    if (
      this.props.location.search !== nextProps.location.search ||
      this.props.state !== nextProps.state
    ) {
      this.setState({ isLoadingRows: true });
      this.setState({ isLoading: true });
      await this.scans(nextProps.location.search, nextProps.state);
      this.setState({ isLoading: false });
      this.setState({ isLoadingRows: false });
    }
  }

  flag = () => {
    if (this.state.selected.length > 0 && this.props.state !== table_user) {
      this.flagSelected();
    }
  };

  async flagSelected() {
    this.setState({ isFlagging: true });
    for (var i in this.state.selected) {
      var transaction_data = this.state.data.find(
        x => x.id === this.state.selected[i]
      );
      var transaction_id = transaction_data.transaction_id;
      await API.put("admin", "/transactions/".concat(transaction_id));

      var transaction = await API.get(
        "admin",
        "/transactions/".concat(transaction_id)
      );

      transaction_data.transaction_status = transaction.transaction_status;
    }

    this.setState({ selected: [] });
    this.handleChangePage(null, 0);
    this.setState({ isFlagging: false });
  }

  async scans(search, state) {
    try {
      const scans = await API.get(
        "admin",
        state === table_user || this.props.state === table_user
          ? "/users".concat(search || this.props.location.search || "")
          : state === table_flagged || this.props.state === table_flagged
          ? "/transactions/flagged"
          : "/transactions".concat(search || this.props.location.search || "")
      );
      counter = 0;
      await this.setState({ data: [], selected: [] });
      if (state === table_user || this.props.state === table_user) {
        for (var i of scans) {
          this.state.data.push(
            createUser(i.username, i.first_name, i.last_name)
          );
        }
      } else {
        for (var i of scans) {
          this.state.data.push(
            createTransaction(
              i.time_created,
              i.transaction_id,
              i.buyer_username,
              i.seller_username,
              i.transaction_status,
              i.note
            )
          );
        }
      }
      this.handleChangePage(null, 0);
    } catch (e) {
      alert(e);
    }
  }

  handleRequestSort = (event, property) => {
    const orderBy = property;
    let order = "desc";

    if (this.state.orderBy === property && this.state.order === "desc") {
      order = "asc";
    }

    this.setState({ order, orderBy });
  };

  handleSelectAllClick = event => {
    if (event.target.checked) {
      this.setState(state => ({ selected: state.data.map(n => n.id) }));
      return;
    }
    this.setState({ selected: [] });
  };

  handleClick = (event, id) => {
    const { selected } = this.state;
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }

    this.setState({ selected: newSelected });
  };

  handleChangePage = (event, page) => {
    this.setState({ isLoadingRows: true });
    this.setState({ page });
    this.setState({ isLoadingRows: false });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value });
  };

  handleManage = () => {
    if (this.state.selected[0] && this.state.selected.length === 1)
      this.props.history.push(
        this.props.state === table_user
          ? "/user/".concat(
              this.state.data.find(x => x.id === this.state.selected[0])
                .username
            )
          : "/transaction/".concat(
              this.state.data.find(x => x.id === this.state.selected[0])
                .transaction_id
            )
      );
  };

  isSelected = id => this.state.selected.indexOf(id) !== -1;

  render() {
    const { classes } = this.props;
    const { data, order, orderBy, selected, rowsPerPage, page } = this.state;
    const emptyRows =
      rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage);

    return !this.state.isLoading ? (
      <Grow
        in={!this.state.isLoading}
        style={{
          transitionDelay: this.state.isLoading ? 0 : 75
        }}
      >
        <Paper className={classes.root}>
          <EnhancedTableToolbar
            numSelected={selected.length}
            handleManage={this.handleManage}
            flag={this.flag}
            isFlagging={this.state.isFlagging}
            state={this.props.state}
          />
          <div className={classes.tableWrapper}>
            <Table className={classes.table} aria-labelledby="tableTitle">
              <EnhancedTableHead
                numSelected={selected.length}
                order={order}
                orderBy={orderBy}
                onSelectAllClick={this.handleSelectAllClick}
                onRequestSort={this.handleRequestSort}
                rowCount={data.length}
                rows={this.props.state === table_user ? user_rows : rows}
              />
              <TableBody>
                {stableSort(data, getSorting(order, orderBy))
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map(n => {
                    const isSelected = this.isSelected(n.id);
                    return this.props.state === table_user ? (
                      <TableRow
                        hover
                        role="checkbox"
                        aria-checked={isSelected}
                        tabIndex={-1}
                        key={n.id}
                        selected={isSelected}
                      >
                        <TableCell padding="checkbox">
                          <Checkbox
                            checked={isSelected}
                            onClick={event => this.handleClick(event, n.id)}
                          />
                        </TableCell>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 75
                          }}
                        >
                          <TableCell>{n.username}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 175
                          }}
                        >
                          <TableCell>{n.name_first}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 275
                          }}
                        >
                          <TableCell>{n.name_last}</TableCell>
                        </Zoom>
                      </TableRow>
                    ) : (
                      <TableRow
                        hover
                        role="checkbox"
                        aria-checked={isSelected}
                        tabIndex={-1}
                        key={n.id}
                        selected={isSelected}
                      >
                        <TableCell padding="checkbox">
                          <Checkbox
                            checked={isSelected}
                            onClick={event => this.handleClick(event, n.id)}
                          />
                        </TableCell>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 75
                          }}
                        >
                          <TableCell>{n.date}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 175
                          }}
                        >
                          <TableCell>{n.transaction_id}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 275
                          }}
                        >
                          <TableCell>{n.buyer}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 375
                          }}
                        >
                          <TableCell>{n.seller}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 475
                          }}
                        >
                          <TableCell>{n.transaction_status}</TableCell>
                        </Zoom>
                        <Zoom
                          in={!this.state.isLoadingRows}
                          style={{
                            transitionDelay: this.state.isLoadingRows ? 0 : 575
                          }}
                        >
                          <TableCell>{n.note}</TableCell>
                        </Zoom>
                      </TableRow>
                    );
                  })}
                {emptyRows > 0 && (
                  <TableRow style={{ height: 49 * emptyRows }}>
                    <TableCell colSpan={7} />
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
          <TablePagination
            component="div"
            count={data.length}
            rowsPerPage={rowsPerPage}
            rowsPerPageOptions={[5, 10, 25, 50]}
            page={page}
            backIconButtonProps={{
              "aria-label": "Previous Page"
            }}
            nextIconButtonProps={{
              "aria-label": "Next Page"
            }}
            onChangePage={this.handleChangePage}
            onChangeRowsPerPage={this.handleChangeRowsPerPage}
          />
        </Paper>
      </Grow>
    ) : (
      <Fragment>
        <LinearProgress color="secondary" className={classes.loader} />
        <LinearProgress
          color="secondary"
          variant="query"
          className={classes.loader}
        />
        <LinearProgress color="secondary" className={classes.loader} />
        <LinearProgress
          color="secondary"
          variant="query"
          className={classes.loader}
        />
        <LinearProgress color="secondary" className={classes.loader} />
        <LinearProgress
          color="secondary"
          variant="query"
          className={classes.loader}
        />
        <LinearProgress color="secondary" className={classes.loader} />
      </Fragment>
    );
  }
}

EnhancedTable.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(EnhancedTable);
