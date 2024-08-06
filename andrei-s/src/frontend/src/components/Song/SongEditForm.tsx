'use client';

import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";
import {Song} from "@/entities/Song";
import {deleteSong, updateSong} from "@/lib/songActions";

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

    return <div>
        <AppField label={"Name"} type="text" value={modifiedName}
                        onChange={(e) => {
                            setModifiedName(e.target.value)
                        }}/>
        <AppField label={"Style"} type="text" value={modifiedStyle}
                         onChange={(e) => {
                             setModifiedStyle(e.target.value)
                         }}/>
        <AppButton className={"mt-1 mr-1"} onClick={submitForm}>Save</AppButton>
        <AppButton className={"mt-1"} onClick={deleteBtn} style={"danger"}>Delete</AppButton>
        <p>{formResult}</p>
    </div>
}