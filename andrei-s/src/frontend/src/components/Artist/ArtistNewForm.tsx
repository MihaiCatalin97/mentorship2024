'use client';

import {AppField} from "@/components/ui/appField";
import React, {useState} from "react";
import {parseArtist} from "@/entities/Artist";
import {newArtist} from "@/lib/artistActions";
import {AppButton} from "@/components/ui/appButton";

export default function ArtistNewForm() {
    const [artistName, setArtistName] = useState("");
    const [formResult, setFormResult] = useState("");

    function submitForm() {
        let artist = parseArtist({});
        artist.name = artistName;
        newArtist(artist).then(() => {
            setFormResult("Successfully created artist");
            setArtistName("");
        }).catch((e) => setFormResult(e.message));
    }

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={artistName}
                  onChange={(e) => {
                      setArtistName(e.target.value)
                  }}/>
        <AppButton onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}