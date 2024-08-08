'use server';

import {Album} from "@/entities/albumEntity";
import {Artist} from "@/entities/artistEntity";
import {Song} from "@/entities/songEntity";

export async function fetchSongs(): Promise<Song[]> {
    let req = await fetch('http://localhost:8080/songs', {
        next: {tags: ['songs']}
    });
    return await req.json();
}

export async function fetchArtists(): Promise<Artist[]> {
    let req = await fetch('http://localhost:8080/artists', {
        next: {tags: ['artists']}
    });
    return await req.json();
}

export async function fetchAlbums(): Promise<Album[]> {
    let req = await fetch('http://localhost:8080/albums', {
        next: {tags: ['albums']}
    });
    return await req.json();
}

export async function fetchAlbum(id: number): Promise<Album | null> {
    let req = await fetch('http://localhost:8080/albums/' + id, {
        next: {tags: ['albums']}
    });
    if (req.status != 200) return null;
    return await req.json();
}

export async function fetchSong(id: number): Promise<Song | null> {
    let req = await fetch('http://localhost:8080/songs/' + id, {
        next: {tags: ['songs']}
    });
    if (req.status != 200) return null;
    return await req.json();
}

export async function fetchArtist(id: number): Promise<Artist | null> {
    let req = await fetch('http://localhost:8080/artists/' + id, {
        next: {tags: ['artists']}
    });
    if (req.status != 200) return null;
    return await req.json();
}

export async function fetchArtistSongs(artistId: number): Promise<Song[]> {
    let req = await fetch('http://localhost:8080/songs?artistId=' + artistId, {
        next: {tags: ['songs']}
    });

    return await req.json();
}

export async function fetchAlbumSongs(albumId: number): Promise<Song[]> {
    let req = await fetch('http://localhost:8080/songs?albumId=' + albumId, {
        next: {tags: ['songs']}
    });

    return await req.json();
}

export async function fetchArtistAlbums(artistId: number): Promise<Album[]> {
    let req = await fetch('http://localhost:8080/albums?artistId=' + artistId, {
        next: {tags: ['albums']}
    });
    return await req.json();
}