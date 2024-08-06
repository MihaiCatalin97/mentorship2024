'use client';

import {Album} from "@/entities/Album";
import {updateAlbum} from "@/lib/albumActions";
import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";

export default function AlbumEditForm({album}: { album: Album }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(album.name);

    async function submitForm() {
        album.name = modifiedName;
        updateAlbum(album).then(() => {
            setFormResult("Successfully edited album");
        }).catch((e) => setFormResult(e.message));
    }

    return <div>
        Name: <AppField className={"ml-1"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <br/>
        <AppButton className={"mt-1"} onClick={submitForm}>Save</AppButton>
        <p>{formResult}</p>
    </div>
}