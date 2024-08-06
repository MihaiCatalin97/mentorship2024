export type Song = {
    id: number;
    name: string;
    album_id: string;
    artist_id: string;
    duration: number;
    style: string;
}

export function parseSong(apiEntity: Record<string, any>): Song {
    return {
        id: apiEntity.id,
        name: apiEntity.name ?? "",
        album_id: apiEntity.album_id ?? 0,
        artist_id: apiEntity.artist_id ?? 0,
        duration: apiEntity.duration ?? 0,
        style: apiEntity.style ?? ""
    };
}

export function parseSongs(apiData: Object): Song[] {
    let entities: Song[] = [];

    if (!Array.isArray(apiData)) return entities;

    apiData.forEach(apiEntity => {
        if (apiEntity.id) entities.push(parseSong(apiEntity));
    })

    return entities;
}