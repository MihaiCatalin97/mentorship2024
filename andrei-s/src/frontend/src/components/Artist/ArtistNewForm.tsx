'use client';

import AppField from "@/components/AppField";
import AppButton from "@/components/AppButton";
import {useState} from "react";
import {parseArtist} from "@/entities/Artist";
import {newArtist} from "@/lib/artistActions";

export default function ArtistNewForm() {
    const [artistName, setArtistName] = useState("");
    const [formResult, setFormResult] = useState("");

    async function submitForm() {
        let artist = parseArtist({});
        artist.name = artistName;
        newArtist(artist).then(() => {
            setFormResult("Successfully created artist");
            setArtistName("");
        }).catch((e) => setFormResult(e.message));
    }

    return <div>
        <AppField label={"Name"} type="text" value={artistName}
                        onChange={(e) => {
                            setArtistName(e.target.value)
                        }}/>
        <AppButton className={"mt-1"} onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}