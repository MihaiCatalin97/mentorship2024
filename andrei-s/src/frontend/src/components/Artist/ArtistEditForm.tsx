'use client';

import {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {Artist} from "@/entities/Artist";
import {deleteArtist, updateArtist} from "@/lib/artistActions";
import {AppButton} from "@/components/ui/appButton";

export default function ArtistEditForm({artist}: { artist: Artist }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(artist.name);

    async function submitForm() {
        artist.name = modifiedName;
        updateArtist(artist).then(() => {
            setFormResult("Successfully edited artist");
        }).catch((e) => setFormResult(e.message));
    }

    function deleteBtn() {
        deleteArtist(artist.id)
            .catch(e => setFormResult(e.message));
    }

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <AppButton onClick={submitForm}>Save</AppButton>
        <AppButton onClick={deleteBtn} variant={"destructive"}>Delete</AppButton>
        <p>{formResult}</p>
    </div>
}