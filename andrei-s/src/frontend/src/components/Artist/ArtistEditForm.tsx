'use client';

import {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {Artist} from "@/entities/Artist";
import {deleteArtist, updateArtist} from "@/lib/artistActions";
import {AppButton, ButtonIcon} from "@/components/ui/appButton";
import {
    Dialog,
    DialogClose,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger
} from "@/components/ui/dialog";
import {EditIcon, TrashIcon} from "lucide-react";
import {updateAlbum} from "@/lib/albumActions";

export default function ArtistEditForm({artist}: { artist: Artist }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(artist.name);

    async function submitForm() {
        const modifiedArtist = Object.assign({}, artist);
        modifiedArtist.name = modifiedName;
        updateArtist(modifiedArtist).then(() => {
            setFormResult("Successfully edited artist");
        }).catch((e) => setFormResult(e.message));
    }

    function deleteBtn() {
        deleteArtist(artist.id)
            .catch(e => setFormResult(e.message));
    }

    return <>
        <div className={"flex flex-row gap-1"}>
            <Dialog>
                <DialogTrigger asChild>
                    <AppButton>
                        <ButtonIcon icon={<EditIcon/>}/>
                        Edit
                    </AppButton>
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Edit Artist</DialogTitle>
                        <DialogDescription>You are editing artist currently named '{artist.name}' with
                            ID {artist.id}</DialogDescription>
                    </DialogHeader>
                    <AppField placeholder={"Name"} type="text" value={modifiedName}
                              onChange={(e) => {
                                  setModifiedName(e.target.value)
                              }}/>
                    <DialogClose asChild>
                        <AppButton onClick={submitForm}>Save</AppButton>
                    </DialogClose>
                </DialogContent>
            </Dialog>
            <AppButton onClick={deleteBtn} variant={"destructive"}>
                <ButtonIcon icon={<TrashIcon/>}/>
                Delete</AppButton>
            <p>{formResult}</p>
        </div>
    </>
}