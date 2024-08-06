'use server';

import {Album, parseAlbum, parseAlbums} from "@/entities/Album";
import {revalidateTag} from "next/cache";

export async function getAlbum(id: number): Promise<Album> {
    let data = await fetch('http://localhost:8080/albums/' + id, {
        next: {
            tags: ['albums']
        }
    });
    return parseAlbum(await data.json());
}

export async function getAlbums(): Promise<Album[]> {
    let data = await fetch('http://localhost:8080/albums', {
        next: {
            tags: ['albums']
        }
    });
    return parseAlbums(await data.json());
}

export async function getAlbumsByArtist(artistId: number): Promise<Album[]> {
    let data = await fetch('http://localhost:8080/albums?artist=' + artistId, {
        next: {
            tags: ['albums']
        }
    });
    return parseAlbums(await data.json());
}

export async function deleteAlbum(id: number): Promise<String> {
    let data = await fetch('http://localhost:8080/albums/' + id, {
        method: "DELETE",
        next: {tags: ['albums']}
    });
    revalidateTag("albums");
    if (data.status != 200) return Promise.reject(new Error(await data.text()));
    return await data.text();
}

export async function updateAlbum(album: Album) {
    let data = await fetch('http://localhost:8080/albums/' + album.id, {
        method: "PUT",
        body: JSON.stringify(album),
        headers: {
            "Content-Type": "application/json"
        }
    });
    if (data.status != 200) {
        return Promise.reject(new Error(await data.text()));
    }
    revalidateTag("albums");

    return await data.text();
}

export async function createAlbum(album: Album) {
    let data = await fetch('http://localhost:8080/albums', {
        method: "POST",
        body: JSON.stringify(album),
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (data.status != 200) {
        return Promise.reject(new Error(await data.text()));
    }
    revalidateTag("albums");

    return await data.text();
}