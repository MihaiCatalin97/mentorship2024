'use client';

import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";
import {Artist} from "@/entities/Artist";
import {updateArtist} from "@/lib/artistActions";

export default function ArtistEditForm({artist}: { artist: Artist }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(artist.name);

    async function submitForm() {
        artist.name = modifiedName;
        updateArtist(artist).then(() => {
            setFormResult("Successfully edited artist");
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