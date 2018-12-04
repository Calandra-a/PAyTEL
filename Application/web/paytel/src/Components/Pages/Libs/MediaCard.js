import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const styles = {
    media: {
        height: "400px"
    }
};

function MediaCard(props) {
    const { classes } = props;
    return (
        <Card>
            <CardContent>
                <Typography gutterBottom variant="h5">
                    {props.title}
                </Typography>
                <img className={classes.media} src={props.link} title={props.title}/>
            </CardContent>
            {props.view && <CardActions>
                <Button onClick={props.view} size="small" color="primary">
                    View User
                </Button>
            </CardActions>}
        </Card>
    );
}

MediaCard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MediaCard);