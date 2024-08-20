import {getSongsByArtist} from "@/lib/songActions";
import SongListTable from "@/components/Song/SongListTable";
import {getArtist} from "@/lib/artistActions";
import ArtistEditForm from "@/components/Artist/ArtistEditForm";
import {getAlbumsByArtist} from "@/lib/albumActions";
import AlbumNewForm from "@/components/Album/AlbumNewForm";
import AlbumList from "@/components/Album/AlbumList";
import {getUser} from "@/lib/loginActions";

export default async function ArtistPage({params}: { params: { id: number } }) {

    let artist = await getArtist(params.id);
    let artistSongs = await getSongsByArtist(artist.id);
    let artistAlbums = await getAlbumsByArtist(artist.id);

    let user = await getUser();

    return (
        <div>
            {
                artist.id &&
                <div>
                    <div className="mb-1 text-2xl">Artist: {artist.name}</div>
                    { user && <ArtistEditForm artist={artist}/> }
                    <div className="mb-1 mt-3">Artist Albums</div>
                    <AlbumList albums={artistAlbums}/>
                    <div className="mb-1 mt-3">Artist Songs</div>
                    <SongListTable songs={artistSongs}/>
                    { user && <>
                        <div className="mb-1 mt-3">Add Album</div>
                        <AlbumNewForm artistId={artist.id}/>
                    </>
                    }
                </div>
            }
            {
                !artist.id &&
                <p>Artist not found :(</p>
            }
        </div>
    );
}
