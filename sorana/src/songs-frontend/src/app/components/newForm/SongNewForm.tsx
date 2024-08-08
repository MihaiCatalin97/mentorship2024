'use client';


import React, {useState} from "react";
import {Song} from "@/entities/songEntity";
import {createSong} from "@/lib/createMethods";
import {Alert, Button, Input, notification} from "antd";
import {InfoCircleOutlined} from "@ant-design/icons";

export default function SongNewForm({albumId}: { albumId: number }) {
    const [api, contextHolder] = notification.useNotification();

    const [songName, setSongName] = useState("");
    const [songDuration, setSongDuration] = useState(0);
    const [songArtistId, setSongArtistId] = useState(0);
    const [songStyle, setSongStyle] = useState("");
    const [result, setResult] = useState<null | string>(null);

    async function createClick() {
        let song: Song = {
            id: 0,
            albumId,
            name: songName,
            duration: songDuration,
            style: songStyle,
            artistId: songArtistId
        };

        setResult(await createSong(song));
        setSongName("");
        setSongDuration(0);
        setSongArtistId(0);
        setSongStyle("");

        api.open({
            message: 'Result',
            description:
            result,
            icon: <InfoCircleOutlined style={{ color: '#108ee9' }} />,
        });
    }

    return <>
        {contextHolder}
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
        <Button onClick={createClick}>Create song</Button>
        {result &&
            <Alert message={result}/>
        }
    </>
}