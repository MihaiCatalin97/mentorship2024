'use server';

import {Artist, parseArtist, parseArtists} from "@/entities/Artist";
import {revalidateTag} from "next/cache";


export async function getArtist(id: number): Promise<Artist> {
    let data = await fetch('http://localhost:8080/artists/' + id, {
        next: {
            tags: ['artists']
        }
    });
    return parseArtist(await data.json());
}

export async function getArtists(): Promise<Artist[]> {
    let data = await fetch('http://localhost:8080/artists', {
        next: {
            tags: ['artists']
        }
    });
    return parseArtists(await data.json());
}

export async function deleteArtist(id: number): Promise<String> {
    let data = await fetch('http://localhost:8080/artists/' + id, {
        method: "DELETE",
        next: {tags: ['artists']}
    });
    revalidateTag("artists");
    if (data.status != 200) return Promise.reject(new Error(await data.text()));
    return await data.text();
}

export async function updateArtist(artist: Artist) {
    let data = await fetch('http://localhost:8080/artists/' + artist.id, {
        method: "PUT",
        body: JSON.stringify(artist),
        headers: {
            "Content-Type": "application/json"
        }
    });
    if (data.status != 200) {
        return Promise.reject(new Error(await data.text()));
    }
    revalidateTag("artists");

    return await data.text();
}

export async function newArtist(artist: Artist) {
    let data = await fetch('http://localhost:8080/artists', {
        method: "POST",
        body: JSON.stringify(artist),
        headers: {
            "Content-Type": "application/json"
        }
    });
    if (data.status != 200) {
        return Promise.reject(new Error(await data.text()));
    }
    revalidateTag("artists");

    return await data.text();
}

export async function searchArtists(query: string): Promise<Artist[]> {
    let data = await fetch('http://localhost:8080/artists/search?query=' + query, {
        next: {tags: ['artists']}
    });

    if (data.status != 200) {
        return Promise.reject(new Error(await data.text()));
    }
    return parseArtists(await data.json());
}
