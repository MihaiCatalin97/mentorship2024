'use client';

import {useState} from "react";
import AppButton from "@/components/AppButton";
import AppField from "@/components/AppField";
import {createSong} from "@/lib/songActions";
import {parseSong} from "@/entities/Song";
import {Artist} from "@/entities/Artist";
import {searchArtists} from "@/lib/artistActions";

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

    return <div>
        Name: <AppField className={"ml-1 mb-1"} type="text" value={songName}
                        onChange={(e) => {
                            setSongName(e.target.value)
                        }}/><br/>
        Style: <AppField className={"ml-1 mb-1"} type="text" value={songStyle}
                         onChange={(e) => {
                             setSongStyle(e.target.value)
                         }}/><br/>
        Duration: <AppField className={"ml-1 mb-1"} type="number" value={songDuration}
                            onChange={(e) => {
                                setSongDuration(parseInt(e.target.value))
                            }}/><br/>
        {
            !artistChosen &&
            <div>
                Search for artist <AppField className={"ml-1 mb-1 mr-1"} type="string" value={artistNameQuery}
                                            onChange={(e) => {
                                                setArtistNameQuery(e.target.value)
                                            }}/>
                <AppButton onClick={searchForArtist}>Search</AppButton><br/>
                {
                    artistsResults.map(artist =>
                        <div>
                            <AppButton onClick={() => chooseArtist(artist)}>Set '{artist.name}'</AppButton>
                        </div>
                    )
                }
            </div>
        }
        {
            artistChosen &&
            <div>
                Artist: {songArtistName} <AppButton onClick={() => setArtistChosen(false)}>Edit</AppButton><br/>
            </div>
        }
        <AppButton className={"mt-1"} onClick={submitForm}>Add</AppButton>
        <p>{formResult}</p>
    </div>
}