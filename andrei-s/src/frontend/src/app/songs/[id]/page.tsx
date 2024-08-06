import {getArtist} from "@/lib/artistActions";
import {getSong} from "@/lib/songActions";
import SongEditForm from "@/components/Song/SongEditForm";
import AppLink from "@/components/AppLink";
import {getAlbum} from "@/lib/albumActions";

export default async function SongPage({params}: { params: { id: number } }) {

    let song = await getSong(params.id);
    let artist = await getArtist(song.artistId);
    let album = await getAlbum(song.albumId);

    return (
        <div>
            {
                song.id &&
                <div>
                    <div className="mb-1 text-xl text-pink-500">Song: {song.name}</div>
                    <div className="text-sm">Style: {song.style}</div>
                    <div className="text-sm">Artist: <AppLink href={`/artists/${artist.id}`}>{artist.name}</AppLink>
                    </div>
                    <div className="text-sm">Album: <AppLink href={`/albums/${album.id}`}>{album.name}</AppLink></div>
                    <div className="mb-1 mt-3 text-xl">Edit Song</div>
                    <SongEditForm song={song}/>
                </div>
            }
            {
                !song.id &&
                    <p>Song not found :(</p>
            }
        </div>
    );
}
