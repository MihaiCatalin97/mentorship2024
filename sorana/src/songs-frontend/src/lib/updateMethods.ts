'use server';

import {Artist} from "@/entities/artistEntity";
import {revalidateTag} from "next/cache";

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