import {Avatar, Card, CardActions, CardContent, CardHeader, IconButton, Typography} from "@mui/material";
import React from "react"
import FavoriteIcon from '@mui/icons-material/Favorite'
import ShareIcon from '@mui/icons-material/Share'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import {blue, green, red} from "@mui/material/colors";


interface CardProps {
    readonly role: string
    readonly name: string
}

export function Profile(props: CardProps) {
    const {role, name} = props

    const getColor = (aRole: string) => {
        switch (aRole) {
            case 'photographer':
                return green[500]
            case 'model':
                return blue[500]
            default:
                return red[500]
        }
    }

    return (
        <Card sx={theme => ({
            width: theme.spacing(60),
            marginTop: theme.spacing(2)
        })}>
            <CardHeader
                avatar={
                    <Avatar sx={{bgcolor: getColor(role)}} aria-label="recipe">
                        {role[0].toUpperCase()}{role[1]}
                    </Avatar>
                }
                action={
                    <IconButton aria-label="settings">
                        <MoreVertIcon/>
                    </IconButton>
                }
                title={name}
                subheader={role}
            />
            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    This is a short description of the user&apos;s profile
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                <IconButton aria-label="add to favorites">
                    <FavoriteIcon/>
                </IconButton>
                <IconButton aria-label="share">
                    <ShareIcon/>
                </IconButton>
            </CardActions>
        </Card>
    )
}