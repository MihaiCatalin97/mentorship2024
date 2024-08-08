'use client';

import {AppCard, CardHeader} from "@/components/ui/appCard";
import {Album} from "@/entities/Album";
import {useRouter} from "next/navigation";

export default function AlbumList({albums}: { albums: Album[] }) {
    const router = useRouter();

    return <div className={"grid grid-cols-3 gap-3"}>
        {albums.map(album =>
            <AppCard onClick={() => router.push(`/albums/${album.id}`)}>
                <CardHeader>{album.name}</CardHeader>
            </AppCard>
        )}
        {
            albums.length == 0 &&
            <div className={"text-sm"}>No albums</div>
        }
    </div>
}