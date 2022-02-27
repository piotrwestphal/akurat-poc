import React, {useEffect, useState} from 'react';
import {Box} from "@mui/material";
import {CreateButton} from "./CreateButton";
import {Profile} from "./Profile";

function App() {
    const [profiles, setProfiles] = useState<Array<{ role: string, name: string }>>([])
    const refresh = async () => {
        const response = await fetch('/api/v1/profiles').then(v => v.json() as Promise<Array<{ role: string, name: string }>>)
        setProfiles(response)
    }

    useEffect(() => {
        refresh()
    }, [])

    return (
        <Box sx={theme => ({
            width: theme.spacing(80),
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            margin: '20px auto',
        })}>
            <CreateButton refresh={refresh}/>
            {profiles.map(v => <Profile key={v.name} role={v.role} name={v.name}/>)}
        </Box>
    );
}

export default App;
