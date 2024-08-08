'use client';

import React, {useState} from "react";
import {Alert, Button, Input, notification} from "antd";
import {Artist} from "@/entities/artistEntity";
import {createArtist} from "@/lib/createMethods";
import NotificationCreate from "@/app/components/NotificationCreate";
import {InfoCircleOutlined, SmileOutlined} from "@ant-design/icons";

export default function ArtistForm() {
    const [api, contextHolder] = notification.useNotification();

    const [artistName, setArtistName] = useState('');
    const [result, setResult] = useState<null | string>(null);

    async function createClick() {
        let artist: Artist = {id: 0, name: artistName};

        setResult(await createArtist(artist));
        setArtistName("");
        api.open({
            message: 'Result',
            description:
                result,
            icon: <InfoCircleOutlined style={{ color: '#108ee9' }} />,
        });
    }

    return <>
        {contextHolder}
        <Input value={artistName}
               placeholder={"Name"}
               onChange={(e) => setArtistName(e.target.value)}/>
        <Button onClick={createClick}>Create artist</Button>
        {result &&
            <Alert message={result}/>
        }
    </>
}