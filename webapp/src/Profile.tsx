import {Avatar, Card, CardActions, CardContent, CardHeader, IconButton, Typography} from "@mui/material";
import React from "react"
import FavoriteIcon from '@mui/icons-material/Favorite'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import DeleteIcon from '@mui/icons-material/Delete';
import {blue, green, red} from "@mui/material/colors";


interface CardProps {
    readonly id: number
    readonly role: string
    readonly name: string
    readonly refresh: () => void
}

export function Profile(props: CardProps) {
    const {id, role, name, refresh} = props

    const getColor = (aRole: string) => {
        switch (aRole) {
            case 'PHOTOGRAPHER':
                return green[500]
            case 'MODEL':
                return blue[500]
            default:
                return red[500]
        }
    }

    const remove = async () => {
        await fetch(`/api/v1/profiles/${id}`,
            {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
        refresh()
    }

    return (
        <Card sx={theme => ({
            width: theme.spacing(60),
            marginTop: theme.spacing(2),
            [theme.breakpoints.down('sm')]: {
                width: '100%',
            }
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
                <IconButton onClick={remove} aria-label="delete">
                    <DeleteIcon/>
                </IconButton>
            </CardActions>
        </Card>
    )
}