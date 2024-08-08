import AppLink from "@/components/AppLink";
import {getAlbums} from "@/lib/albumActions";
import {getArtists} from "@/lib/artistActions";
import React, { Suspense } from "react";
import AlbumList from "@/components/Album/AlbumList";
import ArtistList from "@/components/Artist/ArtistList";
import TestChart from "@/components/TestChart";

export default async function Home() {
    return (
        <div>
            <div className="mt-5 mb-2 text-xl">Albums in database</div>
            <Suspense>
                <AlbumList albums={await getAlbums()}/>
            </Suspense>
            <div className="mt-5 mb-2 text-xl">Artists in database</div>
            <Suspense>
                <ArtistList artists={await getArtists()}/>
            </Suspense>
            <div className="mt-5 mb-2 text-xl">Some charts</div>
        </div>
    );
}
