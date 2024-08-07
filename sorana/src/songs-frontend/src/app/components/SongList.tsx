import { Song } from "@/entities/songEntity";

export default async function SongList({songs}: {songs: Song[]}){
    return (
        <div>
          {songs.map(song => 
            <div>
              {song.name}
            </div>
          )}
        </div>
      );
}