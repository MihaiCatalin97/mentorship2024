'use client';

import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";
import {Song} from "@/entities/Song";
import {updateSong} from "@/lib/songActions";

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

    return <div>
        Name: <AppField className={"ml-1 mt-1"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <br/>
        Style: <AppField className={"ml-1 mt-1"} type="text" value={modifiedStyle}
                         onChange={(e) => {
                             setModifiedStyle(e.target.value)
                         }}/>
        <br/>
        <AppButton className={"mt-1"} onClick={submitForm}>Save</AppButton>
        <p>{formResult}</p>
    </div>
}