import React, {useState} from "react";
import {Box, Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, TextField} from "@mui/material";
import faker from "@faker-js/faker";

interface CreateButtonProps {
    readonly refresh: () => void
}

export function CreateButton(props: CreateButtonProps) {
    const {refresh} = props
    const [name, setName] = useState<string>('')
    const [role, setRole] = useState<string>('')
    const handleChange = (setter: any) => (event: SelectChangeEvent) => {
        setter(event.target.value as string);
    }

    const createProfile = async () => {
        await fetch('/api/v1/profiles',
            {
                method: 'POST',
                body: JSON.stringify({name, role}),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(v => v.json())
        refresh()
        setName('')
        setRole('')
    }

    const generateName = async () => {
        const randomName = faker.name.findName()
        setName(randomName)
    }

    return (
        <Box sx={theme => ({
            width: theme.spacing(40),
            marginBottom: theme.spacing(3),
            display: 'flex',
            flexDirection: 'column',
            [theme.breakpoints.down('sm')]: {
                width: '100%',
            }
        })}>
            <TextField label="Name"
                       variant="outlined"
                       value={name}
                       onChange={handleChange(setName) as any}/>
            <Button sx={theme => ({marginBottom: theme.spacing(2)})}
                    variant="contained"
                    onClick={generateName}>Generate name</Button>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Profile role</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={role}
                    label="Profile role"
                    onChange={handleChange(setRole) as any}
                >
                    <MenuItem value='DESIGNER'>Designer</MenuItem>
                    <MenuItem value='MODEL'>Photo model</MenuItem>
                    <MenuItem value='PHOTOGRAPHER'>Photographer</MenuItem>
                </Select>
            </FormControl>
            <Button disabled={role === '' || name === ''} variant="contained" onClick={createProfile}>Create
                Profile</Button>
        </Box>
    )
}