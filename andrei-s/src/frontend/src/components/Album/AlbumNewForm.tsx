'use client';

import {parseAlbum} from "@/entities/Album";
import {createAlbum} from "@/lib/albumActions";
import {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {AppButton} from "@/components/ui/appButton";

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

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={albumName}
                        onChange={(e) => {
                            setAlbumName(e.target.value)
                        }}/>
        <AppButton onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}