import SongList from "@/app/components/SongList";
import AlbumList from "@/app/components/AlbumList";
import { fetchAlbum, fetchAlbumSongs, fetchArtist, fetchArtistAlbums, fetchArtistSongs } from "@/lib/fetchMethods";
import { Artist } from "@/entities/artistEntity";

import {Card} from "antd";

import AlbumEditForm from "@/app/components/editFrom/AlbumEditForm";
import AlbumNewForm from "@/app/components/newForm/AlbumNewForm";
import React from "react";
import SongNewForm from "@/app/components/newForm/SongNewForm";

export default async function AlbumPage({params}: {params : {id:number}}){
    const albumId = params.id;

    const album = await fetchAlbum(albumId);
    let artist: Artist | null = null;

    if (album == null) return <div>Album not found.</div>
    if (album.artistId) artist = await fetchArtist(album.artistId);

    const albumSongs = await fetchAlbumSongs(album.id);


    const artistAlbums = await fetchArtistAlbums(album.id);

    return <div>
        {/*Album: {album.name}<br/>*/}
        {/*{artist &&*/}
        {/*    <div>*/}
        {/*        By artist: {artist.name}*/}
        {/*        <p>Songs: </p>*/}
        {/*        <SongList songs={albumSongs} />*/}
        {/*        <SongNewForm albumId={album.id} />*/}
        {/*    </div>*/}
        {/*}*/}
        <div className={"max-w-xl"}>
            <Card className={"mb-5"} title={`Album: ${album.name}`}>
                <SongList songs={albumSongs}/>
                <AlbumList albums={artistAlbums}/>
            </Card>
            <AlbumEditForm album={album}/>
            <SongNewForm albumId={album.id}/>
        </div>
    </div>
}