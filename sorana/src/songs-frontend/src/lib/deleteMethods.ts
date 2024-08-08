'use server';

import {revalidateTag} from "next/cache";

export async function deleteArtist(artistId : number){
    let req = await fetch("http://localhost:8080/artists/" + artistId, {
        method: "DELETE"
    })
    revalidateTag("artists");

    return await req.text();
}
export async function deleteAlbum(albumId : number){
    let req = await fetch("http://localhost:8080/albums/" + albumId, {
        method: "DELETE"
    })
    revalidateTag("albums");

    return await req.text();
}