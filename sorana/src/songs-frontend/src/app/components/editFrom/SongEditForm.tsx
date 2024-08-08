'use client';
import {useState} from "react";
import {updateSong} from "@/lib/updateMethods";
import {deleteSong} from "@/lib/deleteMethods";
import {Alert, Button, Input} from "antd";
import {Song} from "@/entities/songEntity";


export default function SongEditForm({song}: { song: Song }) {
    const [songName, setSongName] = useState(song.name);
    const [songDuration, setSongDuration] = useState(song.duration);
    const [songArtistId, setSongArtistId] = useState(song.artistId);
    const [songStyle, setSongStyle] = useState(song.style);
    const [result, setResult] = useState<null | string>(null);

    async function updateClick() {
        song.name = songName;
        song.duration = songDuration;
        song.style = songStyle;
        song.artistId = songArtistId;
        setResult(await updateSong(song));
    }


    async function deleteClick() {

        setResult(await deleteSong(song.id));
    }

    return <>
        <Input value={songName}
               placeholder={"Name"}
               onChange={(e) => setSongName(e.target.value)}/>
        <Input value={songDuration}
               placeholder={"Duration"}
               type={"number"}
               onChange={(e) => setSongDuration(parseInt(e.target.value))}/>
        <Input value={songStyle}
               placeholder={"Style"}
               onChange={(e) => setSongStyle(e.target.value)}/>
        <Input value={songArtistId}
               placeholder={"Artist Id"}
               type={"number"}
               onChange={(e) => setSongArtistId(parseInt(e.target.value))}/>
        <Button onClick={updateClick}>Update artist</Button>
        <Button onClick={deleteClick} danger={true}>Delete artist</Button>

        {result &&
            <Alert message={result}/>
        }
    </>
}