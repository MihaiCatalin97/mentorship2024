import AppLink from "@/components/AppLink";
import {Song} from "@/entities/Song";

export default async function SongList({
                                           songs
                                       }: {
    songs: Song[]
}) {
    return (
        <div className="shadow border border-gray-600 rounded-lg text-sm">
            {songs.map(song =>
                <div className="px-2 py-1">
                    <AppLink href={`/songs/${song.id}`}>
                        {song.name} <span className="text-pink-300">({song.style})</span>
                    </AppLink>
                </div>
            )}
            {songs.length == 0 &&
                <div className={"px-2 py-1 text-xs"}>No Songs</div>
            }
        </div>
    );
}
