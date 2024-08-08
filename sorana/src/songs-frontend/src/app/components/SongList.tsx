import {Song} from "@/entities/songEntity";
import Link from "next/link";
import {Button} from "antd";

export default async function SongList({songs}: { songs: Song[] }) {
    return (
        <div>
            {songs.map(song =>
                <div>
                    <Button className={"mb-1"} type={"link"} size={"large"}>
                        <Link href={`/songs/${song.id}`} passHref={true}>song - {song.name} (id: {song.id})</Link>
                    </Button>
                </div>
            )}
        </div>
    );
}