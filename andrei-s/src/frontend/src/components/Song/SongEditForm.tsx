'use client';

import React, {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {Song} from "@/entities/Song";
import {deleteSong, updateSong} from "@/lib/songActions";
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

export default function SongEditForm({song}: { song: Song }) {
    const [formResult, setFormResult] = useState("");

    const [modifiedName, setModifiedName] = useState(song.name);
    const [modifiedStyle, setModifiedStyle] = useState(song.style);

    async function submitForm() {
        const modifiedSong = Object.assign({}, song);
        modifiedSong.name = modifiedName;
        modifiedSong.style = modifiedStyle;
        updateSong(modifiedSong).then(() => {
            setFormResult("Successfully edited song");
        }).catch((e) => setFormResult(e.message));
    }

    function deleteBtn() {
        deleteSong(song.id).catch(e => setFormResult(e.message));
    }

    return <>
        <div className={"flex flex-row gap-1"}>
            <Dialog>
                <DialogTrigger asChild>
                    <AppButton>
                        <ButtonIcon icon={<EditIcon/>}/>
                        Edit</AppButton>
                </DialogTrigger>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Edit Song</DialogTitle>
                        <DialogDescription>You are editing song named currently '{song.name}' with
                            ID {song.id}</DialogDescription>
                    </DialogHeader>
                    <AppField placeholder={"Name"} type="text" value={modifiedName}
                              onChange={(e) => {
                                  setModifiedName(e.target.value)
                              }}/>
                    <AppField placeholder={"Style"} type="text" value={modifiedStyle}
                              onChange={(e) => {
                                  setModifiedStyle(e.target.value)
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