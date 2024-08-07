'use client';

import {useState} from "react";
import {Alert, Button, Input} from "antd";
import {Artist} from "@/entities/artistEntity";
import {createArtist} from "@/lib/createMethods";

export default function ArtistForm(){
    const [artistName, setArtistName] = useState('');
    const [result, setResult] = useState<null | string>(null);

    async function createClick() {
        let artist: Artist = { id: 0, name: artistName };

        setResult(await createArtist(artist));
        setArtistName("");
    }

    return <>
        <Input value={artistName}
               placeholder={"Name"}
               onChange={(e) => setArtistName(e.target.value)} />
        <Button onClick={createClick}>Create artist</Button>
        { result &&
            <Alert message={result} />
        }
    </>
}