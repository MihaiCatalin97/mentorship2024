export type Song = {
    id: number;
    name: string;
    albumId: number;
    artistId: number;
    duration: number;
    style: string;
}

export function parseSong(apiEntity: Record<string, any>): Song {
    return {
        id: apiEntity.id,
        name: apiEntity.name ?? "",
        albumId: apiEntity.albumId ?? 0,
        artistId: apiEntity.artistId ?? 0,
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

