import { Album } from "@/entities/albumEntity";
import Link from "next/link";

export default async function ArtistList({albums}: {albums: Album[]}){
    return (
        <div>
          {albums.map(album => 
            <div>
              <Link href={`/albums/${album.id}`}>{album.name} (id: {album.id})</Link>
            </div>
          )}
        </div>
      );
}