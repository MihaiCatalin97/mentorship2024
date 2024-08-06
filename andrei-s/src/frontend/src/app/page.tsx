'use client';

import {Album, parseAlbums} from "@/entities/Album";
import {useEffect, useState} from "react";
import Link from 'next/link';
import AppLink from "@/components/AppLink";

export default function Home() {
    const [albums, setAlbums] = useState<Album[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/albums')
            .then((res) => res.json())
            .then((data: object) => {
                setAlbums(parseAlbums(data));
            })
    }, []);

    return (
        <div>
            <div className="mb-5 text-xl">Songs Database</div>
            <div className="mb-3 text-md">Albums in database</div>
            {albums.map(album =>
                <div>
                    <AppLink href={`/albums/${album.id}`}
                          className="text-sm underline decoration-pink-500/50 hover:decoration-pink-500">{album.name}</AppLink>
                </div>
            )}
        </div>
    );
}
