import {Album} from "@/entities/albumEntity";
import Link from "next/link";
import {Button} from "antd";

export default function ArtistList({albums}: { albums: Album[] }) {
    return (
        <div>
            {albums.map(album =>
                <div>
                    <Button className={"mb-1"} size={"large"}>
                        <Link href={`/artists/${album.id}`} passHref={true}>album - {album.name} (id: {album.id})</Link>
                    </Button>
                </div>
            )}
        </div>
    );
}