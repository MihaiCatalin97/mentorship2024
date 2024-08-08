'use client';

import React, {useState} from "react";
import {Alert, Button, Input, notification} from "antd";
import {Album} from "@/entities/albumEntity";
import {createAlbum} from "@/lib/createMethods";
import {InfoCircleOutlined} from "@ant-design/icons";

export default function AlbumNewForm({artistId}: { artistId: number }) {
    const [api, contextHolder] = notification.useNotification();

    const [albumName, setAlbumName] = useState("");
    const [result, setResult] = useState<null | string>(null);

    async function createClick() {
        let album: Album = {id: 0, artistId, name: albumName};

        setResult(await createAlbum(album));
        setAlbumName("");

        api.open({
            message: 'Result',
            description:
            result,
            icon: <InfoCircleOutlined style={{ color: '#108ee9' }} />,
        });
    }

    return <>
        {contextHolder}
        <Input value={albumName}
               placeholder={"Name"}
               onChange={(e) => setAlbumName(e.target.value)}/>
        <Button onClick={createClick}>Create album</Button>
        {result &&
            <Alert message={result}/>
        }
    </>
}