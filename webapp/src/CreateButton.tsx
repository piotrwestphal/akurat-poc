import React, {useState} from "react";
import {Box, Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent} from "@mui/material";

interface CreateButtonProps {
    readonly refresh: () => void
}

export function CreateButton(props: CreateButtonProps) {
    const {refresh} = props
    const [role, setRole] = useState<string>('')
    const handleChange = (event: SelectChangeEvent) => {
        setRole(event.target.value as string);
    }

    const createProfile = async () => {
        await fetch('/api/v1/profiles',
            {
                method: 'POST',
                body: JSON.stringify({text: role}),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(v => v.json())
        refresh()
    }

    return (
        <Box sx={theme => ({
            width: theme.spacing(20),
            marginBottom: theme.spacing(3),
            display: 'flex',
            flexDirection: 'column'
        })}>
            <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Profile role</InputLabel>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={role}
                    label="Profile role"
                    onChange={handleChange}
                >
                    <MenuItem value='DESIGNER'>Designer</MenuItem>
                    <MenuItem value='MODEL'>Photo model</MenuItem>
                    <MenuItem value='PHOTOGRAPHER'>Photographer</MenuItem>
                </Select>
            </FormControl>
            <Button disabled={role === ''} variant="contained" onClick={createProfile}>Create Profile</Button>
        </Box>
    )
}