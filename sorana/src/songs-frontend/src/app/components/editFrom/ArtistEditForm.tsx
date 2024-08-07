'use client';

import {useState} from "react";
import {Alert, Button, Input} from "antd";
import {Artist} from "@/entities/artistEntity";
import createArtist from "@/lib/createMethods";
import {updateArtist} from "@/lib/updateMethods";
import {deleteArtist} from "@/lib/deleteMethods";

export default function ArtistEditForm({artist}: {artist: Artist}){
    const [artistName, setArtistName] = useState(artist.name);
    const [result, setResult] = useState<null | string>(null);

    async function updateClick() {
        artist.name = artistName;

        setResult(await updateArtist(artist));
    }

    async function deleteClick() {

        setResult(await deleteArtist(artist.id));
    }

    return <>
        <Input value={artistName}
               placeholder={"Name"}
               onChange={(e) => setArtistName(e.target.value)} />
        <Button onClick={updateClick}>Update artist</Button>
        <Button onClick={deleteClick} danger={true}>Delete artist</Button>

        { result &&
            <Alert message={result} />
        }
    </>
}