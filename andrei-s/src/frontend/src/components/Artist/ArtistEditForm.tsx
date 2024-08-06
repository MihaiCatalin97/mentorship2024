'use client';

import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";
import {Artist} from "@/entities/Artist";
import {deleteArtist, updateArtist} from "@/lib/artistActions";

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

    return <div>
        <AppField label={"Name"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <AppButton className={"mt-1 mr-1"} onClick={submitForm}>Save</AppButton>
        <AppButton className={"mt-1"} onClick={deleteBtn} style={"danger"}>Delete</AppButton>
        <p>{formResult}</p>
    </div>
}