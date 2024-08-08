'use server';

import {Artist} from "@/entities/artistEntity";
import {revalidateTag} from "next/cache";
import {Album} from "@/entities/albumEntity";

export async function updateArtist(artist : Artist){
    console.log(artist);
    let req = await fetch("http://localhost:8080/artists/" + artist.id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(artist)
    })
    revalidateTag("artists");

    return await req.text();
}

export async function updateAlbum(album:Album){
    let req =  await fetch("http://localhost:8080/albums/" + album.id,{
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(album)
    })
    revalidateTag("albums");

    return await req.text();
}