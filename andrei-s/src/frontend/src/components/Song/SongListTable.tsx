'use client';

import {Song} from "@/entities/Song";
import {Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table";
import {useRouter} from "next/navigation";

export default function SongListTable({ songs }: { songs: Song[] }) {
    const router = useRouter();

    return (
        <Table>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">ID</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Style</TableHead>
                    <TableHead className="text-right">Duration</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>

                {songs.map(song =>
                    <TableRow onClick={() => router.push(`/songs/${song.id}`)} className={"cursor-pointer"}>
                        <TableCell className="font-medium">{song.id}</TableCell>
                        <TableCell>{song.name}</TableCell>
                        <TableCell>{song.style}</TableCell>
                        <TableCell className="text-right">{song.duration}</TableCell>
                    </TableRow>
                )}
                {
                    songs.length == 0 &&
                    <TableRow>
                        <TableCell className="font-medium">No songs</TableCell>
                    </TableRow>
                }

            </TableBody>
        </Table>

    );
}
