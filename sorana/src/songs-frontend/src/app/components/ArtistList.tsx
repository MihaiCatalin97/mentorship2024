import {Artist} from "@/entities/artistEntity";
import Link from "next/link";
import {Button} from "antd";

export default async function ArtistList({artists}: { artists: Artist[] }) {
    return (
        <div>
            {artists.map(artist =>
                <div>
                    <Button className={"mb-1"}  size={"large"}>
                        <Link href={`/artists/${artist.id}`} passHref={true}>artist - {artist.name} (id: {artist.id})</Link>
                    </Button>
                </div>
            )}
        </div>
    );
}