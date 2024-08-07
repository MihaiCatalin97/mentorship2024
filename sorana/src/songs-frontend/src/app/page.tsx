import { Song } from "@/entities/songEntity";
import Image from "next/image";
import SongList from "./components/SongList";

import { fetchArtists, fetchSongs } from "@/lib/fetchMethods";
import ArtistList from "./components/ArtistList";
import {Button} from "antd";
import ArtistForm from "@/app/components/newForm/ArtistNewForm";


export default async function Home() {

  const artists =  await fetchArtists(); 

  return (
    <div>
            <ArtistList artists={artists} />

            <ArtistForm />
    </div>
  )
}


