'use client';

import {Album} from "@/entities/Album";
import {deleteAlbum, updateAlbum} from "@/lib/albumActions";
import {useState} from "react";
import {AppField} from "@/components/ui/appField";
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

export default function AlbumEditForm({album}: { album: Album }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(album.name);

    function submitForm() {
        const modifiedAlbum = Object.assign({}, album);
        modifiedAlbum.name = modifiedName;
        updateAlbum(modifiedAlbum)
            .then(() => setFormResult("Successfully edited album"))
            .catch((e) => setFormResult(e.message));
    }

    function deleteButton() {
        deleteAlbum(album.id)
            .catch((e) => setFormResult(e.message));
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
                        <DialogTitle>Edit Album</DialogTitle>
                        <DialogDescription>You are editing album currently named '{album.name}' with
                            ID {album.id}</DialogDescription>
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
            <AppButton onClick={() => deleteButton()} variant={"destructive"}>
                <ButtonIcon icon={<TrashIcon/>}/>
                Delete Album</AppButton>
            <p>{formResult}</p>
        </div>
    </>
}