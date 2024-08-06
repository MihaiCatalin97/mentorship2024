'use client';

import {parseAlbum} from "@/entities/Album";
import {createAlbum} from "@/lib/albumActions";
import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";

export default function AlbumNewForm({artistId}: { artistId: number }) {
    const [formResult, setFormResult] = useState("");
    const [albumName, setAlbumName] = useState("");

    async function submitForm() {
        let album = parseAlbum({});
        album.name = albumName;
        album.artistId = artistId;
        createAlbum(album).then(() => {
            setFormResult("Successfully created album");
            setAlbumName("");
        }).catch((e) => setFormResult(e.message));
    }

    return <div>
        <AppField label={"Name"} type="text" value={albumName}
                        onChange={(e) => {
                            setAlbumName(e.target.value)
                        }}/>
        <AppButton className={"mt-1"} onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}