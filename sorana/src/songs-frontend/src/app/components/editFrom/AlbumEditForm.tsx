'use client';
import {Album} from "@/entities/albumEntity";
import {useState} from "react";
import {Button, Input} from "antd";
import {updateAlbum} from "@/lib/updateMethods";
import {deleteAlbum} from "@/lib/deleteMethods";

export default function ArtistEditForm({album}:{album:Album}){
    const [albumName,setAlbumName] = useState("")
    const [result,setResult] = useState<null |string>(null)

    async function updateClick(){
        album.name=albumName;
        setResult(await updateAlbum(album));
    }
    async function deleteClick(){

        setResult(await deleteAlbum(album.id));
    }

    return <>
        <Input value={albumName}
               placeholder={"Name Album"}
               onChange={(e) =>setAlbumName(e.target.value)}/>
        <Button onClick={updateClick}>Update Album</Button>
        <Button onClick={deleteClick} danger={true}>Delete Album</Button>
    </>
}