'use server';

import {revalidateTag} from "next/cache";
import {Artist} from "@/entities/artistEntity";
import {Album} from "@/entities/albumEntity";
import {Song} from "@/entities/songEntity";

export async function createArtist(artist: Artist): Promise<string> {
    let req = await fetch("http://localhost:8080/artists", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(artist)
    })
    revalidateTag("artists");

    return await req.text();
}

export async function createAlbum(album: Album): Promise<string> {
    let req = await fetch("http://localhost:8080/albums", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(album)
    })
    revalidateTag("albums");

    return await req.text();
}

export async function createSong(song: Song): Promise<string> {
    let req = await fetch("http://localhost:8080/songs", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(song)
    })
    revalidateTag("songs");

    return await req.text();
}