import SongListTable from "@/components/Song/SongListTable";
import AlbumEditForm from "@/components/Album/AlbumEditForm";
import {getAlbum} from "@/lib/albumActions";
import AppLink from "@/components/AppLink";
import {getSongsByAlbum} from "@/lib/songActions";
import {getArtist} from "@/lib/artistActions";
import SongNewForm from "@/components/Song/SongNewForm";

export default async function AlbumPage({params}: { params: { id: number } }) {

    let album = await getAlbum(params.id);
    let artist = await getArtist(album.artistId);
    let albumSongs = await getSongsByAlbum(album.id);

    return (
        <div>
            {
                album.id &&
                <div>
                    <div className="mb-1 text-xl text-pink-500">Album: {album.name}</div>
                    <div className="text-sm">Artist: <AppLink href={`/artists/${artist.id}`}>{artist.name}</AppLink>
                    </div>
                    <div className="mb-1 mt-3 text-md">Album Songs</div>
                    <SongListTable songs={albumSongs}/>
                    <div className="mb-1 mt-3 text-xl">Add Song</div>
                    <SongNewForm albumId={album.id}/>
                    <div className="mb-1 mt-3 text-xl">Edit Album</div>
                    <AlbumEditForm album={album}/>
                </div>
            }
            {
                !album.id &&
                    <p>Album not found :(</p>
            }
        </div>
    );
}
