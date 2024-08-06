import {getSongsByArtist} from "@/lib/songActions";
import SongList from "@/components/Song/SongList";
import {getArtist} from "@/lib/artistActions";
import ArtistEditForm from "@/components/Artist/ArtistEditForm";
import {getAlbumsByArtist} from "@/lib/albumActions";
import AppLink from "@/components/AppLink";
import AlbumNewForm from "@/components/Album/AlbumNewForm";

export default async function ArtistPage({params}: { params: { id: number } }) {

    let artist = await getArtist(params.id);
    let artistSongs = await getSongsByArtist(artist.id);
    let artistAlbums = await getAlbumsByArtist(artist.id);

    return (
        <div>
            <div>
                <div className="mb-1 text-xl text-pink-500">Artist: {artist.name}</div>
                <div className="mb-1 mt-3">Artist Albums</div>
                {artistAlbums.map(album =>
                    <div>
                        <AppLink href={`/albums/${album.id}`}>{album.name}</AppLink>
                    </div>
                )}
                {artistAlbums.length == 0 &&
                    <div className={"text-xs"}>No Albums</div>
                }
                <div className="mb-1 mt-3">Artist Songs</div>
                <SongList songs={artistSongs}/>
                <div className="mb-1 mt-3">Add Album</div>
                <AlbumNewForm artistId={artist.id}/>
                <div className="mb-1 mt-3">Edit Artist</div>
                <ArtistEditForm artist={artist}/>
            </div>
        </div>
    );
}
