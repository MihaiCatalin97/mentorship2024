import SongList from "@/app/components/SongList";
import AlbumList from "@/app/components/AlbumList";
import { fetchArtist, fetchArtistAlbums, fetchArtistSongs } from "@/lib/fetchMethods";
import {Button, Card} from "antd";
import ArtistEditForm from "@/app/components/editFrom/ArtistEditForm";
import AlbumNewForm from "@/app/components/newForm/AlbumNewForm";


export default async function ArtistPage({params}: {params : {id:number}}){
    const artistId = params.id;

    const artist = await fetchArtist(artistId);
  
    if (artist == null) return <div>Artist not found.</div>

    const artistSongs = await fetchArtistSongs(artist.id);
    const artistAlbums = await fetchArtistAlbums(artist.id);

    return <div className={"max-w-xl"}>
        <Card className={"mb-5"} title={`Artist: ${artist.name}`}>
            <SongList songs={artistSongs}/>
            <AlbumList albums={artistAlbums}/>
        </Card>
        <ArtistEditForm artist={artist}/>
        <AlbumNewForm artistId={artist.id} />
    </div>
}