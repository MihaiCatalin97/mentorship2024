'use server';

import {parseSong, parseSongs, Song} from "@/entities/Song";
import {revalidateTag} from "next/cache";
import {cookies} from "next/headers";

export async function getSongsByAlbum(id: number): Promise<Song[]> {
    let data = await fetch('http://localhost:8080/songs?album=' + id, {
        next: {tags: ['songs']}
    });
    return parseSongs(await data.json());
}

export async function getSongsByArtist(id: number): Promise<Song[]> {
    let data = await fetch('http://localhost:8080/songs?artist=' + id, {
        next: {tags: ['songs']}
    });
    return parseSongs(await data.json());
}

export async function getSong(id: number): Promise<Song> {
    let data = await fetch('http://localhost:8080/songs/' + id, {
        next: {tags: ['songs']}
    });
    return parseSong(await data.json());
}

export async function deleteSong(id: number): Promise<String> {
    let data = await fetch('http://localhost:8080/songs/' + id, {
        method: "DELETE",
        next: {tags: ['songs']},
        headers: { "Cookie": "JSESSIONID=" + cookies().get("JSESSIONID")?.value }
    });
    revalidateTag("songs");
    if (data.status == 401) return Promise.reject(new Error("Unauthorized"));
    if (data.status != 200) return Promise.reject(new Error(await data.text()));
    return await data.text();
}

export async function updateSong(song: Song) {
    let data = await fetch('http://localhost:8080/songs/' + song.id, {
        method: "PUT",
        body: JSON.stringify(song),
        headers: {
            "Content-Type": "application/json",
            "Cookie": "JSESSIONID=" + cookies().get("JSESSIONID")?.value
        }
    });
    if (data.status == 401) return Promise.reject(new Error("Unauthorized"));
    if (data.status != 200) return Promise.reject(new Error(await data.text()));

    revalidateTag("songs");

    return await data.text();
}

export async function createSong(song: Song) {
    let data = await fetch('http://localhost:8080/songs', {
        method: "POST",
        body: JSON.stringify(song),
        headers: {
            "Content-Type": "application/json",
            "Cookie": "JSESSIONID=" + cookies().get("JSESSIONID")?.value
        }
    });

    if (data.status == 401) return Promise.reject(new Error("Unauthorized"));
    if (data.status != 200) return Promise.reject(new Error(await data.text()));

    revalidateTag("songs");

    return await data.text();
}

