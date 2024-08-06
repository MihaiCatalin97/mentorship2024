import AppLink from "@/components/AppLink";
import {getAlbums} from "@/lib/albumActions";
import {getArtists} from "@/lib/artistActions";

export default async function Home() {
    let albums = await getAlbums();
    let artists = await getArtists();

    return (
        <div>
            <div className="mt-3 mb-1 text-xl">Albums in database</div>
            {albums.map(album =>
                <div>
                    <AppLink href={`/albums/${album.id}`}
                             className="text-sm underline decoration-pink-500/50 hover:decoration-pink-500">{album.name}</AppLink>
                </div>
            )}
            <div className="mt-3 mb-1 text-xl">Artists in database</div>
            {artists.map(artist =>
                <div>
                    <AppLink href={`/artists/${artist.id}`}
                             className="text-sm underline decoration-pink-500/50 hover:decoration-pink-500">{artist.name}</AppLink>
                </div>
            )}
        </div>
    );
}
