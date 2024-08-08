'use client';

import {Album} from "@/entities/Album";
import {deleteAlbum, updateAlbum} from "@/lib/albumActions";
import {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {AppButton} from "@/components/ui/appButton";

export default function AlbumEditForm({album}: { album: Album }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(album.name);

    function submitForm() {
        album.name = modifiedName;
        updateAlbum(album)
            .then(() => setFormResult("Successfully edited album"))
            .catch((e) => setFormResult(e.message));
    }

    function deleteButton() {
        deleteAlbum(album.id)
            .catch((e) => setFormResult(e.message));
    }

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <AppButton className={"mt-1 mr-1"} onClick={submitForm}>Save</AppButton>
        <AppButton onClick={() => deleteButton()} variant={"destructive"}>Delete Album</AppButton>
        <p>{formResult}</p>
    </div>
}