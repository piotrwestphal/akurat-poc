import React, {useEffect, useState} from 'react';
import {Box} from "@mui/material";
import {CreateButton} from "./CreateButton";
import {Profile} from "./Profile";

function App() {
    const [profiles, setProfiles] = useState<Array<{ id: number, role: string, name: string }>>([])
    const refresh = async () => {
        const response = await fetch('/api/v1/profiles').then(v => v.json() as Promise<Array<{ id: number, role: string, name: string }>>)
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
            [theme.breakpoints.down('sm')]: {
                margin: '20px 0',
                width: '100%'
            }
        })}>
            <CreateButton refresh={refresh}/>
            {profiles.map(v => <Profile key={v.id}
                                        id={v.id}
                                        role={v.role}
                                        name={v.name}
                                        refresh={refresh}/>)}
        </Box>
    );
}

export default App;