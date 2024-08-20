import ArtistNewForm from "@/components/Artist/ArtistNewForm";
import {getUser} from "@/lib/loginActions";
import {redirect} from "next/navigation";

export default async function NewArtist() {

    const user = await getUser();

    if (user == null) redirect("/login");

    return <div>
        <div className={"mb-1 text-xl"}>Create a new artist</div>
        <ArtistNewForm/>
    </div>
}