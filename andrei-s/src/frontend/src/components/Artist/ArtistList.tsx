'use client';

import {AppCard, CardHeader} from "@/components/ui/appCard";
import {useRouter} from "next/navigation";
import {Artist} from "@/entities/Artist";

export default function ArtistList({artists}: { artists: Artist[] }) {
    const router = useRouter();

    return <div className={"grid grid-cols-3 gap-3"}>
        {artists.map(artist =>
            <AppCard onClick={() => router.push(`/artists/${artist.id}`)}>
                <CardHeader>{artist.name}</CardHeader>
            </AppCard>
        )}
        {
            artists.length == 0 &&
            <div className={"text-sm"}>No artists</div>
        }
    </div>
}