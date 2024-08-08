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
                    <div className="mb-1 text-2xl">Album: {album.name}</div>
                    <AlbumEditForm album={album}/>
                    <div className="mt-3 text-sm">Artist: <AppLink
                        href={`/artists/${artist.id}`}>{artist.name}</AppLink></div>
                    <div className="mb-1 mt-3 text-md">Album Songs</div>
                    <SongListTable songs={albumSongs}/>
                    <div className="mb-1 mt-3 text-xl">Add Song</div>
                    <SongNewForm albumId={album.id}/>
                </div>
            }
            {
                !album.id &&
                <p>Album not found :(</p>
            }
        </div>
    );
}
