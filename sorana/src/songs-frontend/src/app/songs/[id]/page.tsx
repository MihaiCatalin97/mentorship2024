import {fetchAlbum, fetchArtist, fetchSong} from "@/lib/fetchMethods";
import {Card} from "antd";
import SongEditForm from "@/app/components/editFrom/SongEditForm";
import React from "react";

export default async function SongPage({params}: { params: { id: number } }) {

    const song = await fetchSong(params.id);
    if (song == null) return <div>Song not found.</div>

    const album = await fetchAlbum(song.albumId);
    const artist = await fetchArtist(song.artistId)

    return (
        <div>
            {/*<Button>*/}
            {/*    <Link href={"/artist/1"} passHref={true}>*/}
            {/*        artist 1*/}
            {/*    </Link>*/}
            {/*</Button>*/}
            {/*Song: {song.name}<br />*/}
            {/*{*/}
            {/*    album &&*/}
            {/*        <Link href={`/albums/${album.id}`}>Album: {album.name}</Link>*/}
            {/*}*/}
            {/*<br />*/}
            {/*{*/}
            {/*    artist &&*/}
            {/*        <Link href={`/artists/${artist.id}`}>Artist: {artist.name}</Link>*/}
            {/*}*/}

            <div className={"max-w-xl"}>
                <Card className={"mb-5"} title={`Song: ${song.name}`}>

                </Card>
                <SongEditForm song={song}/>
            </div>
        </div>
    )
}