'use client';

import {Album, parseAlbum} from "@/entities/Album";
import {useEffect, useState} from "react";
import {useRouter} from "next/router";
import {Artist, parseArtist} from "@/entities/Artist";
import {parseSongs, Song} from "@/entities/Song";
import AppLink from "@/components/AppLink";

export default function AlbumPage({params}: { params: { id: string } }) {

    const [album, setAlbum] = useState<Album | null>(null);
    const [artist, setArtist] = useState<Artist | null>(null);
    const [songs, setSongs] = useState<Song[] | null>(null);

    useEffect(() => {
        fetch('http://localhost:8080/albums/' + params.id)
            .then((res) => res.json())
            .then((data: object) => {
                setAlbum(parseAlbum(data));
            })
    }, [params.id]);

    useEffect(() => {
        if (!album) return;
        fetch('http://localhost:8080/artists/' + album.artist_id)
            .then((res) => res.json())
            .then((data: object) => {
                setArtist(parseArtist(data));
            })
    }, [album]);

    useEffect(() => {
        if (!album) return;
        fetch('http://localhost:8080/songs?album=' + album.id)
            .then((res) => res.json())
            .then((data: object) => {
                setSongs(parseSongs(data));
            })
    }, [album, artist]);

    return (
        <div>
            {(album && artist && songs) ? <div>
                <div className="mb-1 text-md text-pink-500">Album: {album.name}</div>
                <div className="mb-3 text-sm">Artist: {artist.name}</div>
                <div className="mb-1 text-md">Album Songs</div>
                <ul className="border border-gray-600 rounded-lg text-sm">
                    {songs.map(song =>
                        <div className="px-2 py-1">
                            <AppLink href={`/songs/${song.id}`}>
                                {song.name} <span className="text-pink-300">({song.style})</span>
                            </AppLink>
                        </div>
                    )}
                </ul>
            </div> : <div>Loading...</div>}
        </div>
    );
}
