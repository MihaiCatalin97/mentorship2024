'use client';

import React, {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {createSong} from "@/lib/songActions";
import {parseSong} from "@/entities/Song";
import {Artist} from "@/entities/Artist";
import {searchArtists} from "@/lib/artistActions";
import {AppButton} from "@/components/ui/appButton";
import {ScrollArea} from "@/components/ui/scroll-area";
import {AppCard, CardContent} from "@/components/ui/appCard";
import {Separator} from "@/components/ui/separator";

export default function SongNewForm({albumId}: { albumId: number }) {
    const [formResult, setFormResult] = useState("");
    const [songName, setSongName] = useState("");
    const [songDuration, setSongDuration] = useState(0);
    const [songStyle, setSongStyle] = useState("");
    const [songArtistId, setSongArtistId] = useState(0);
    const [songArtistName, setSongArtistName] = useState("");
    const [artistChosen, setArtistChosen] = useState(false);
    const [artistNameQuery, setArtistNameQuery] = useState("");
    const [artistsResults, setArtistsResults] = useState<Artist[]>([]);

    async function submitForm() {
        let song = parseSong({});
        song.name = songName;
        song.albumId = albumId;
        song.artistId = songArtistId;
        song.style = songStyle;
        song.duration = songDuration;
        createSong(song).then(() => {
            setFormResult("Successfully created song");
            setSongName("");
            setSongArtistId(0);
            setSongDuration(0);
            setSongStyle("");
            setArtistChosen(false);
            setArtistNameQuery("");
            setArtistsResults([]);
            setSongArtistId(0);
        }).catch((e) => setFormResult(e.message));
    }

    async function searchForArtist() {
        setArtistsResults(await searchArtists(artistNameQuery));
    }

    function chooseArtist(artist: Artist) {
        setArtistChosen(true);
        setSongArtistName(artist.name);
        setSongArtistId(artist.id);
    }

    return <div className={"flex flex-col gap-2"}>
        <AppField placeholder={"Name"} type="text" value={songName}
                  onChange={(e) => {
                      setSongName(e.target.value)
                  }}/>
        <AppField placeholder={"Style"} type="text" value={songStyle}
                  onChange={(e) => {
                      setSongStyle(e.target.value)
                  }}/>
        <AppField placeholder={"Duration"} type="number" value={songDuration}
                  onChange={(e) => {
                      setSongDuration(parseInt(e.target.value))
                  }}/>
        {
            !artistChosen &&
            <div>
                <AppField placeholder={"Artist name"} type="string" value={artistNameQuery}
                          onChange={(e) => {
                              setArtistNameQuery(e.target.value)
                          }}>
                    <AppButton onClick={searchForArtist}>Search</AppButton>
                </AppField>
                <ScrollArea className={"w-full h-[200px] border rounded-md mt-2"}>
                    <div className={"mt-2 mx-3"}>
                        {
                            artistsResults.map(artist =>
                                <div>
                                <span className={"cursor-pointer"} onClick={() => chooseArtist(artist)}>Select
                                    <b> {artist.name}</b></span>
                                    <Separator />
                                </div>
                            )
                        }
                    </div>
                </ScrollArea>
            </div>
        }
        {
            artistChosen &&
            <div>
                Artist: {songArtistName} <AppButton onClick={() => setArtistChosen(false)}>Edit</AppButton><br/>
            </div>
        }
        <AppButton className={"w-full"} onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}