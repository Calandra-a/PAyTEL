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
        height: 450,
        transform: "rotate(-90deg)"
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
            </CardContent>
            <CardMedia
                className={classes.media}
                image={props.link}
                title={props.title}
            />
            {props.view && <CardActions>
                <Button size="small" color="primary">
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