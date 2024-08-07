'use client';

import {useState} from "react";
import {Alert, Button, Input} from "antd";
import {Album} from "@/entities/albumEntity";
import {createAlbum} from "@/lib/createMethods";

export default function AlbumNewForm({artistId}:{artistId:number}) {
    const [albumName, setAlbumName] = useState("");
    const [result, setResult] = useState<null | string>(null);

    async function createClick() {
        let album : Album = {id: 0, artistId, name: albumName};

        setResult(await createAlbum(album));
        setAlbumName("");
    }

    return <>
        <Input value={albumName}
               placeholder={"Name"}
               onChange={(e) => setAlbumName(e.target.value)} />
        <Button onClick={createClick}>Create album</Button>
        { result &&
            <Alert message={result} />
        }
    </>
}