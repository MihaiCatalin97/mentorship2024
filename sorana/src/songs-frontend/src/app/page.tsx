import {fetchArtists} from "@/lib/fetchMethods";
import ArtistList from "./components/ArtistList";
import ArtistForm from "@/app/components/newForm/ArtistNewForm";


export default async function Home() {

    const artists = await fetchArtists();

    return (
        <div>
            <p>Artists: </p>
            <ArtistList artists={artists}/>

            <ArtistForm/>
        </div>
    )
}


