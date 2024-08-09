'use client';
import React, { useState } from 'react';
import {Album} from "@/entities/albumEntity";
import {Song} from "@/entities/songEntity";
import SongList from "@/app/components/SongList";
import AlbumList from "@/app/components/AlbumList";
import { Card } from 'antd';



function ArtistTabs({songs, albums}:{ songs: Song[], albums: Album[] }) {
    const tabList = [
        {
            key: 'songs',
            tab: 'songs',
        },
        {
            key: 'albums',
            tab: 'albums',
        },
    ];

    const contentList: Record<string, React.ReactNode> = {
        songs: <SongList songs={songs}/>,
        albums:  <AlbumList  albums={albums}/>,
    };


        const [activeTabKey1, setActiveTabKey1] = useState<string>('songs');

        const onTab1Change = (key: string) => {
            setActiveTabKey1(key);
        }

    return (
        <>
            <Card
                style={{ width: '100%' }}
                title="Artists"
                tabList={tabList}
                activeTabKey={activeTabKey1}
                onTabChange={onTab1Change}
                >
                {contentList[activeTabKey1]}
            </Card>

        </>
    );
};

export default ArtistTabs;