'use client';

import React, {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {Song} from "@/entities/Song";
import {deleteSong, updateSong} from "@/lib/songActions";
import {AppButton} from "@/components/ui/appButton";

export default function SongEditForm({song}: { song: Song }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(song.name);
    const [modifiedStyle, setModifiedStyle] = useState(song.style);

    async function submitForm() {
        song.name = modifiedName;
        song.style = modifiedStyle;
        updateSong(song).then(() => {
            setFormResult("Successfully edited song");
        }).catch((e) => setFormResult(e.message));
    }

    function deleteBtn() {
        deleteSong(song.id).catch(e => setFormResult(e.message));
    }

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <AppField placeholder={"Style"} type="text" value={modifiedStyle}
                         onChange={(e) => {
                             setModifiedStyle(e.target.value)
                         }}/>
        <AppButton onClick={submitForm}>Save</AppButton>
        <AppButton onClick={deleteBtn} variant={"destructive"}>Delete</AppButton>
        <p>{formResult}</p>
    </div>
}